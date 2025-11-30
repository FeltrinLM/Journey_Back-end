# -----------------------------------------------------------------------------
# ESTÁGIO 1: Build (Compilação)
# Usamos uma imagem Maven com Java 21 para compilar o projeto
# -----------------------------------------------------------------------------
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copia apenas o pom.xml primeiro para aproveitar o cache de dependências do Docker
COPY pom.xml .

# Baixa as dependências (isso faz o build ser mais rápido nas próximas vezes)
RUN mvn dependency:go-offline

# Copia o código fonte
COPY src ./src

# Compila o projeto e gera o .jar (pula os testes para agilizar o build do container)
RUN mvn clean package -DskipTests

# -----------------------------------------------------------------------------
# ESTÁGIO 2: Runtime (Execução)
# Usamos uma imagem leve apenas com o JRE 21 para rodar o app
# -----------------------------------------------------------------------------
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia o .jar gerado no estágio anterior para a pasta atual renomeando para app.jar
# O nome 'Journey_Back-end-0.0.1-SNAPSHOT.jar' vem do seu pom.xml
COPY --from=build /app/target/Journey_Back-end-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]