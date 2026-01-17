#!/bin/sh

echo "Running unit tests..."
./mvnw test

if [ $? -eq 0 ]; then
    echo "Tests passed! Starting application..."
    ./mvnw spring-boot:run
else
    echo "Tests failed! Exiting..."
    exit 1
fi
