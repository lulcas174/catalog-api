#!/bin/sh

echo "****************************************************"
cat <<'EOF'
        _.---._    /\
      ./'       "--`\//
    ./              o \           .-----.
   /./\  )______   \__ \         ( PostgreSQL )
  ./  / /\ \   | \ \  \ \        '-----'
     / /  \ \  | |\ \  \7
      "     "    "  "      Waiting for DB...
EOF
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
    cat <<'EOF'
        (•_•)
        ( •_•)>⌐■-■
        (⌐■_■)

      ALL TESTS PASSED ✔
EOF
    echo "****************************************************"
    
    ./mvnw -q spring-boot:run &
    APP_PID=$!
    
    echo "Waiting for application to start..."
    
    MAX_WAIT=60
    WAIT_COUNT=0
    
    while [ $WAIT_COUNT -lt $MAX_WAIT ]; do
        if wget --spider --quiet http://127.0.0.1:8080/v1/actuator/health/readiness 2>/dev/null; then
            echo "****************************************************"
            echo "✔ Application is ready!"
            echo "****************************************************"
            wait $APP_PID
            exit $?
        fi
        sleep 1
        WAIT_COUNT=$((WAIT_COUNT + 1))
    done
    
    echo "****************************************************"
    echo "X Application failed to start within ${MAX_WAIT} seconds"
    echo "****************************************************"
    kill $APP_PID 2>/dev/null
    exit 1
else
    echo "****************************************************"
    cat <<'EOF'
        (╯°□°）╯︵ ┻━┻

      TESTS FAILED ✖
      Who wrote this code?
      (probably you)
EOF
    echo "****************************************************"
    exit 1
fi