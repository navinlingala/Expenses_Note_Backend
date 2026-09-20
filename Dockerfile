# Multi-stage build for Spring Boot with Java 21
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copy maven wrapper and pom.xml
COPY remainder/.mvn/ .mvn/
COPY remainder/mvnw remainder/pom.xml ./
RUN chmod +x ./mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code and build jar
COPY remainder/src ./src
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the built jar from build stage
COPY --from=build /app/target/remainder-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Render automatically sets $PORT)
EXPOSE 8080

# Run Spring Boot app
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar app.jar"]
