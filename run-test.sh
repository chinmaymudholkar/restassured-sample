#!/bin/bash

# Navigate to project root
cd "$(dirname "$0")"

# Check if the image exists
if ! podman image exists rest-assured-runner; then
    echo "Image not found. Building..."
    podman build -t rest-assured-runner .
fi

# Run the tests
echo "Running tests..."
podman run --rm -v "$(pwd):/app:Z" rest-assured-runner mvn test