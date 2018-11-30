#Launches the following
# Docker container for customerresourceservice
export KNAPP_WORK_ENVIRONMENT=local

mvn clean compile package -DskipTests

ECHO START stopping running resumeapi docker image
docker stop resumeapi
ECHO END stopping running resumeapi docker image
ECHO START removing running resumeapi docker image
docker rm resumeapi
ECHO END removing running resumeapi docker image


ECHO START build image
docker build -t resumeapi .
ECHO END build image

ECHO START running docker image for resumeapi
docker run -d -p 8080:8080 --name resumeapi -e KNAPP_WORK_ENVIRONMENT resumeapi
ECHO END running docker image for resumeapi

ECHO START wait for app to start
sleep 12s # Waits for the app to start
ECHO END wait for app to start


ECHO START checking logs
docker logs --since 15m resumeapi
ECHO END checking logs

open http://localhost:8080/



