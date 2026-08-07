#!/bin/bash

cd "$(dirname "$0")"

if ! podman image exists rest-assured-runner; then
    podman build --no-cache -t rest-assured-runner .
fi

echo "Running tests..."
podman run --rm -v "$(pwd):/app:Z" rest-assured-runner mvn test

# Generate report using the CLI command (Bypassing the pom.xml configuration)
# Look for XML files in the surefire-reports directory
podman run --rm -v "$(pwd):/app:Z" rest-assured-runner /bin/bash -c "allure generate target/surefire-reports --clean -o target/allure-report"

echo "Open the report at: $(pwd)/target/allure-report/index.html"