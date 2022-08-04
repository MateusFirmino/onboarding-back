FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine

VOLUME /tmp
COPY ./build/libs/app.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=dev","-jar","/app.jar"]
