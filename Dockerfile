FROM openjdk:8u171-jre


LABEL ostk.app.name CustomerResourceService
LABEL ostk.app.type webservice

ENV TZ=America/Denver
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY ./CustomerResourceService-web/target/CustomerResourceService-*.jar /app/run.jar

WORKDIR /app

EXPOSE 8080

CMD java -XX:+UnlockExperimentalVMOptions \
   	-XX:+UseCGroupMemoryLimitForHeap \
   	-XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=/app/java_pid%p.hprof \
   	-jar run.jar;


