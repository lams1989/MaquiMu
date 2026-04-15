# ---- Stage 1: Build ----
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copiar archivos de Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copiar módulos
COPY dominio dominio
COPY aplicacion aplicacion
COPY infraestructura infraestructura
COPY src src

# Dar permisos y construir
RUN chmod +x gradlew && ./gradlew bootJar --no-daemon -x test

# ---- Stage 2: Run ----
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]
