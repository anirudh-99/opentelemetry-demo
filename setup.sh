#!/bin/bash

# Initialize variables with default values
build_images=false
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CHART_PATH="${SCRIPT_DIR}/my-chart"

# Parse command line arguments
while getopts "b" flag; do
    case "${flag}" in
        b) build_images=true ;;
        *) 
            echo "Usage: $0 [-b]"
            echo "  -b: Build and load Docker images"
            exit 1 ;;
    esac
done

if [ "$build_images" = true ]; then
    echo "Building and loading Docker images..."
    cd my-producer
    docker build --build-arg JAR_FILE="my-producer-0.0.1-SNAPSHOT.jar" -t producer-image . || exit 1
    cd ../my-consumer
    docker build --build-arg JAR_FILE="my-consumer-0.0.1-SNAPSHOT.jar" -t consumer-image . || exit 1
    kind load docker-image producer-image consumer-image || exit 1
else
    echo "Skipping image build..."
fi

echo "Deploying Helm chart..."
helm uninstall my-release
helm install my-release "$CHART_PATH"