#!/bin/sh

echo "****************************************************"
echo "WAITING FOR DATABASE..."
echo "****************************************************"

while ! nc -z db 5432; do
  sleep 1
done

echo "Database started!"

echo "****************************************************"
echo "STARTING UNIT TESTS..."
echo "****************************************************"

./mvnw test -B

TEST_EXIT_CODE=$?

if [ $TEST_EXIT_CODE -eq 0 ]; then
    echo "****************************************************"
    echo "TESTS PASSED! STARTING APPLICATION..."
    echo "****************************************************"
    ./mvnw -q spring-boot:run
else
    echo "****************************************************"
    echo "TESTS FAILED! EXITING..."
    echo "****************************************************"
    exit 1
fi
