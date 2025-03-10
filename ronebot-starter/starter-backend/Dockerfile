FROM openjdk:17-jdk-slim AS builder
WORKDIR /app
COPY . /app
RUN ./gradlew :ronebot-starter:starter-backend:build

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/ronebot-starter/starter-backend/build/libs/app.jar /app/app.jar

EXPOSE 9099

CMD ["java", "-jar", "/app/app.jar"]
