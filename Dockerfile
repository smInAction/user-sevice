FROM openjdk:11
MAINTAINER smtrust.com
COPY target/user-service-0.0.1-SNAPSHOT.jar user-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/user-service-0.0.1-SNAPSHOT.jar"]