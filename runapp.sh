#Launches the following
# Docker container for customerresourceservice
export OSTK_ENVIRONMENT=local

mvn clean compile package -DskipTests

ECHO START stopping running customerresourceservice docker image
docker stop customerresourceservice
ECHO END stopping running customerresourceservice docker image
ECHO START removing running customerresourceservice docker image
docker rm customerresourceservice
ECHO END removing running customerresourceservice docker image


ECHO START build image
docker build -t customerresourceservice .
ECHO END build image

ECHO START running docker image for customerresourceservice
docker run -d -p 8080:8080 --name customerresourceservice -e OSTK_ENVIRONMENT customerresourceservice
ECHO END running docker image for customerresourceservice

ECHO START wait for app to start
sleep 12s # Waits for the app to start
ECHO END wait for app to start


ECHO START checking logs
docker logs --since 15m customerresourceservice
ECHO END checking logs

open http://localhost:8080/swagger-ui.html



