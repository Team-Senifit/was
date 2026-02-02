# ============================================
# Build stage
# ============================================
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# Gradle wrapper + build config (캐시 활용)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY global-enum.gradle .

# 소스 복사
COPY src src

# 빌드 (의존성 캐시: build.gradle 등 변경 시에만 재다운로드)
RUN chmod +x gradlew && ./gradlew bootJar --no-daemon

# ============================================
# Runtime stage
# ============================================
FROM eclipse-temurin:17-jre-alpine

RUN addgroup -g 1000 app && adduser -u 1000 -G app -D app
WORKDIR /app

# JAR만 복사
COPY --from=builder /app/build/libs/app.jar app.jar

# SSL/인증서 등 마운트용 (선택)
# RUN mkdir -p /app/cert && chown -R app:app /app

USER app

# SERVER_PORT는 application.yml에서 환경변수로 주입 (기본 8080)
EXPOSE 8443 8443

ENTRYPOINT ["java", "-jar", "app.jar"]
