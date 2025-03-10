FROM openjdk:21-jdk-slim AS builder

WORKDIR /app
COPY . /app

RUN chmod +x ./gradlew && ./gradlew build

FROM openjdk:21-jdk-slim

WORKDIR /app
COPY --from=builder /app/ronebot-starter/starter-backend/build/libs/app.jar /app/app.jar

EXPOSE 9099

CMD ["java", "-jar", "/app/app.jar"]
