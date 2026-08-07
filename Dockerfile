FROM maven:3.9.9-eclipse-temurin-21

# Switch to root for installation
USER root

# Install dependencies using apt-get (Debian/Ubuntu based)
RUN apt-get update && apt-get install -y --no-install-recommends \
    unzip \
    wget \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Download and Install Allure CLI
ENV ALLURE_VERSION=2.27.0
RUN wget -q "https://github.com/allure-framework/allure2/releases/download/${ALLURE_VERSION}/allure-${ALLURE_VERSION}.tgz" \
    && tar -xzf "allure-${ALLURE_VERSION}.tgz" \
    && mv "allure-${ALLURE_VERSION}" /usr/local/allure \
    && ln -s /usr/local/allure/bin/allure /usr/bin/allure \
    && rm "allure-${ALLURE_VERSION}.tgz"

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
COPY .env .

# Ensure target directory exists
RUN mkdir -p /app/target

# Command: Run tests, generate report, start server
CMD ["/bin/bash", "-c", "set -e && mvn test && allure generate target/surefire-reports --clean -o target/allure-report && allure serve target/allure-results --port 8080"]