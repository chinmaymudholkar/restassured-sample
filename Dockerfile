FROM maven:3.9.9-eclipse-temurin-21

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
COPY .env .

# Ensure target directory exists
RUN mkdir -p /app/target

# Command: Run tests, generate report, start server
CMD ["mvn", "test"]