# =========================================
# STAGE 1: Build
# =========================================
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Kopier Maven wrapper og pom.xml først (caching)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (cached hvis pom.xml ikke ændres)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Kopier kildekode
COPY src src

# Build application (skip tests - de køres separat i CI)
RUN ./mvnw package -DskipTests -B

# =========================================
# STAGE 2: Run
# =========================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Opret non-root bruger for sikkerhed
RUN addgroup -g 1001 gymrat && \
    adduser -u 1001 -G gymrat -D gymrat

# Kopier JAR fra build stage
COPY --from=build /app/target/*.jar app.jar

# Skift til non-root bruger
USER gymrat

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/login || exit 1

# Start application
ENTRYPOINT ["java", "-jar", "app.jar"]