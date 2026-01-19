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
    ./mvnw -q spring-boot:run
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