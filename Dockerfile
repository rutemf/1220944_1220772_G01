# Etapa 1: Build da aplicação
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copia apenas o pom.xml para aproveitar cache de dependências
COPY pom.xml .

# Baixa dependências primeiro (melhora performance entre builds)
RUN mvn -B dependency:go-offline

# Copia o código-fonte
COPY src ./src

# Compila e empacota a aplicação (gera o .jar)
RUN mvn -B clean package -DskipTests

# Etapa 2: Runtime (imagem leve)
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copia o JAR gerado da etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta configurada no app
EXPOSE 4677

# Permite passar opções JVM dinamicamente
ENV JAVA_OPTS=""

# Comando de arranque
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]