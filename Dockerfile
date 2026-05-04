# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-focal AS build

WORKDIR /app

# Copy gradle files first for better caching
COPY gradlew .
COPY gradle gradle
COPY settings.gradle.kts .
COPY build.gradle.kts .
COPY gradle.properties .

# Copy source modules needed by server
COPY shared shared
COPY core core
COPY feature feature
COPY server server

# Make gradlew executable and build
RUN chmod +x gradlew && ./gradlew :server:installDist --no-daemon

# Stage 2: Run with minimal image
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the build output from the build stage
COPY --from=build /app/server/build/install/server /app

# Create directory for file uploads
RUN mkdir -p /app/uploads

# Expose the port
EXPOSE 8080

# JVM tuning for 256MB container (Back4app free tier)
# -Xms96m: initial heap 96MB
# -Xmx128m: max heap 128MB
# -XX:MaxMetaspaceSize=48m: limit class metadata
# -XX:+UseSerialGC: lowest overhead GC for small heaps
# -Xss256k: reduce thread stack size
ENV JAVA_OPTS="-Xms96m -Xmx128m -XX:MaxMetaspaceSize=48m -XX:+UseSerialGC -Xss256k -XX:ReservedCodeCacheSize=32m"

# Run the server with JVM flags
CMD ["sh", "-c", "./bin/server $JAVA_OPTS"]
