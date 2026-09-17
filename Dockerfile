# 1. Sử dụng Java Runtime (JRE) nhẹ
FROM eclipse-temurin:25-jre-alpine

# 2. Tạo thư mục làm việc bên trong container
WORKDIR /app

# 3. Copy file JAR từ máy host vào container
COPY /build/libs/*.jar app.jar

# 4. Mở cổng 8080 cho container
EXPOSE 8080

# 5. Lệnh khởi chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]