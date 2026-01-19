# Etapa de build
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copia arquivos necessários para o build
COPY gradle gradle
COPY gradlew gradlew
COPY gradlew.bat gradlew.bat
COPY gradle.properties gradle.properties
COPY build.gradle build.gradle
COPY settings.gradle settings.gradle
COPY src src

# Executa o build sem os testes
RUN chmod +x gradlew \
    && ./gradlew --no-daemon clean build -x test

# Etapa de execução
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia o JAR gerado na etapa de build
COPY --from=builder /app/build/libs/desafio-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Configura o entrypoint da aplicação
ENTRYPOINT ["java", \
  "-XX:+UseG1GC", \
  "-XX:MaxGCPauseMillis=200", \
  "-XX:InitialRAMPercentage=40", \
  "-XX:MaxRAMPercentage=70", \
  "-XX:+ExitOnOutOfMemoryError", \
  "-jar", \
  "app.jar"]
