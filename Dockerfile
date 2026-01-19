FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Install bash, netcat, and wget for entrypoint.sh, health checks, and Maven compatibility
RUN apk add --no-cache bash netcat-openbsd wget

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B
COPY src ./src
RUN ./mvnw test-compile -B
COPY entrypoint.sh ./
RUN chmod +x entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]
