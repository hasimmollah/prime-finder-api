FROM openjdk:17-jdk-alpine
RUN mkdir -p /usr/src/prime-finder-api
COPY prime-finder-api-0.0.1-SNAPSHOT.jar /usr/src/prime-finder-api
WORKDIR /usr/src/prime-finder-api
EXPOSE 9080
ENTRYPOINT ["java","-jar","prime-finder-api-0.0.1-SNAPSHOT.jar"]