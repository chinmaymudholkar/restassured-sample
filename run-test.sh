#!/bin/bash
cd "$(dirname "$0")"

# Clean up old images
podman rmi rest-assured-runner

# Build if image doesn't exist or force rebuild
podman build -t rest-assured-runner .

# Run tests
podman run --rm -v "$(pwd):/app:Z" rest-assured-runner mvn test
