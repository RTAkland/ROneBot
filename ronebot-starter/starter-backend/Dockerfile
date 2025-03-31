FROM openjdk:11-jdk-slim AS builder

WORKDIR /app
COPY . /app

RUN chmod +x ./gradlew && ./gradlew :ronebot-starter:starter-backend:generateResources :ronebot-starter:starter-backend:linkReleaseExecutableLinuxX64

FROM openjdk:11-jdk-slim

WORKDIR /app
COPY --from=builder /app/ronebot-starter/starter-backend/build/bin/linuxX64/releaseExecutable/starter-backend.kexe /app/starter-backend.kexe

RUN chmod +x /app/starter-backend.kexe

EXPOSE 9099

CMD ["./starter-backend.kexe"]
