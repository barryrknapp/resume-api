def call(String serviceUrl, String message) {
  node {
    echo "Running acceptance tests against ${serviceUrl}"

    unstash "AcceptanceTests"
    
    globalsFileContents = """
      {
        "values": [
          {
            "key": "host",
            "value": "${serviceUrl}",
            "type": "text",
            "enabled": true
          }
        ],
        "_postman_variable_scope": "globals"
      }
    """

    docker.image('postman/newman_alpine33').inside("--entrypoint=''") {
      projectDirectory = pwd()            
      globalsFilePath = "${projectDirectory}/CustomerResourceService-web/target/postman.globals"
      collectionFilePath = "${projectDirectory}/CustomerResourceService-web/src/test/resources/postman/CustomerResourceServiceAcceptanceTests.postman_collection"

      writeFile file: "${globalsFilePath}", text: "${globalsFileContents}"
      writtenContents = readFile "${globalsFilePath}"
      echo "Wrote postman globals file to ${globalsFilePath} with contents\n${writtenContents}"
      sh "newman run ${collectionFilePath} -g ${globalsFilePath}"
    }
  }

}

return this
