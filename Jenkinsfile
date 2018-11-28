#!/usr/bin/env groovy
@Library('ostk-pipeline')
import com.overstock.dx.Pipeline

def areDeploying = env.BRANCH_NAME == 'master'

new Pipeline(this).withMaven('3.5.0').withJava('8').execute {
  installWebhooks(scm)

  node {
    // Checkout code, cleaning the tree first if necessary
    ostkCheckout()
    
    stash name: "AcceptanceTests", includes: "CustomerResourceService-web/src/test/resources/postman/"

    if (! areDeploying) {
      stage('Build') {
        timeout(time: 5, unit: 'MINUTES') {
          mvnCleanVerify()
        }
      }
    }
    else {
      String newVersion

      stage('Build') {
        newVersion = ostkSetVersions()
        timeout(time: 10, unit: 'MINUTES') {
          mvnCleanDeploy()
        }

        image = docker.build('customerresourceservice')
        image.tag(newVersion)

        // Optional; perform sanity checks on generated image; will abort the build on failure, but pass 'false' to
        // allow build to continue anyway
        imageChecks()
      }

      stage('Tag/Push') {
        // This will both tag your docker image (if not already) as well as your source
        // The image will also be pushed to the registry
        ostkTagRelease(newVersion)
      }
      clmScan()
    }
  }

  if (areDeploying) {
    String serviceUrl

    stage('Deploy (Pre-prod)') {
      // This step may take a while and will not return until the app's health check passes.  Don't panic if this
      // takes a few minutes!
      def deployResult = deploy.start('prod')
      if (deployResult) {
        serviceUrl = deployResult.serviceUrl
        echo "Service url: ${serviceUrl}"
      } else {
        error "Could not execute initial deploy"
      }
    }

    // Everything needed to ensure we're good to go to production
    stage('Acceptance') {
      try {
        timeout(time:1,unit:'HOURS'){
        // By default, this will do nothing except require a manual button push.  This is sub-optimal in a CD pipeline
        // and is for illustration purposes only; read the documentation for this step to implement properly, or
        // remove the step if there is nothing suitable to do here.
        ostkAcceptanceTests(serviceUrl)
        }
      }
      catch (Exception e) {
        echo "Acceptance testing was aborted or failed; attempting to cancel deploy"
        deploy.cancel()
        throw e
      }

      ostkPerfTests()
      if (currentBuild.currentResult != "SUCCESS") {
        echo "Load testing was aborted or failed; attempting to cancel deploy"
        deploy.cancel()
      }
    }

    // needs to be outside a stage to cancel whole build and not just the stage
    if (currentBuild.currentResult != "SUCCESS") {
      echo "Build status is not SUCCESS. Aborting deploy to prod."
      return
    }

    stage('Deploy (Prod)') {
      success = deploy.finish()
      if (! success) {
        error "Could not execute production deploy"
      }
    }
  }
}
