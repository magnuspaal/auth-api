FROM openjdk:17-alpine
ARG APP_VERSION

COPY ./target/auth-api-${APP_VERSION}.jar /usr/local/lib/auth-api.jar

EXPOSE 8080

ENTRYPOINT java -jar /usr/local/lib/auth-api.jar