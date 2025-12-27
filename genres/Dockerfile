FROM openjdk:17-jdk-slim
WORKDIR /app
ARG JAR_FILE=target/app.jar
COPY ${JAR_FILE} /app/app.jar
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=$SERVER_PORT -jar /app/app.jar"]