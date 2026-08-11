#!/bin/bash

cd "$(dirname "$0")"

if ! podman image exists rest-assured-runner; then
    podman build --no-cache -t rest-assured-runner .
fi

echo "Running tests..."
podman run --rm -v "$(pwd):/app:Z" rest-assured-runner mvn test