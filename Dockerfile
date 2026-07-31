# Use a lightweight OpenJDK 17 image with Maven
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Set working directory inside the container
WORKDIR /app

# Copy only the build definition first to leverage Docker layer caching
COPY pom.xml .

# Download dependencies (this layer caches until pom.xml changes)
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build and run tests inside the container
CMD ["mvn", "test"]