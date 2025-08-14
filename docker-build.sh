#!/bin/bash

# Build the Docker image
echo "Building Docker image..."
docker build -t aut-lifafaend2end . || { echo "Docker build failed"; exit 1; }

# Run the Docker container
echo "Running Docker container..."
docker run --rm aut-lifafaend2end || { echo "Docker run failed"; exit 1; }

echo "Build and run completed successfully."