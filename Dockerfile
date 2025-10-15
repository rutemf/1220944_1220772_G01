FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copia apenas os ficheiros necessários para resolver dependências
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Baixa dependências primeiro (aproveita cache entre builds)
RUN ./mvnw -B dependency:go-offline

# Copia o resto do código
COPY src ./src

# Compila e empacota a aplicação (gera o .jar)
RUN ./mvnw -B clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copia o jar gerado do estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Define a porta da app (ajusta conforme o application.properties)
EXPOSE 4677

# Permite passar opções JVM dinamicamente (ex: -Xmx512m)
ENV JAVA_OPTS=""

# Comando de arranque
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]