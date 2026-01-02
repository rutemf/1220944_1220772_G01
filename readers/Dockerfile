FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
ARG SKIP_TESTS=true
RUN if [ "$SKIP_TESTS" = "true" ]; then mvn -B clean package -DskipTests; else mvn -B clean package; fi

FROM eclipse-temurin:21-jdk
WORKDIR /app
LABEL service="readers"
ARG JAR_FILE=target/*.jar
COPY --from=build /app/${JAR_FILE} app.jar
ENV SERVER_PORT=8082
ENV JAVA_OPTS=""
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=$SERVER_PORT -jar /app/app.jar"]