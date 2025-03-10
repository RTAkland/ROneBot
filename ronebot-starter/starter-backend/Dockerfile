FROM openjdk:11-jdk-slim AS builder

WORKDIR /app
COPY . /app

RUN chmod +x ./gradlew && ./gradlew :ronebot-starter:starter-backend:buildFatJar

FROM openjdk:11-jdk-slim

WORKDIR /app
COPY --from=builder /app/ronebot-starter/starter-backend/build/libs/app.jar /app/app.jar

EXPOSE 9099

CMD ["java", "-jar", "/app/app.jar"]
