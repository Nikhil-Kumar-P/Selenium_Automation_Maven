#!/bin/sh

# Start Xvfb in the background
Xvfb :99 -ac &

# Run the Maven tests
mvn test
