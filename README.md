1.Đóng gói ứng dụng thành file JAR:
Mở Terminal tại thư mục gốc của dự án và chạy lệnh đóng gói ứng dụn Với Gradle:
  - ./gradlew bootJar -x test 
  - Cách xác minh: Kiểm tra thư mục build/libs/ (đối với Gradle) thấy xuất hiện file đuôi .jar (ví dụ: app-0.0.1-SNAPSHOT.jar).

2.Build Docker Image
- docker build -t my-spring-app .

3 Tạo PostgreSQL imange
- docker run -d --name postgres-learning -p 5432:5432 -e POSTGRES_DB=sb_learning -e POSTGRES_USER=postgres_root -e POSTGRES_PASSWORD=2eb862e7d16f4da7 postgres:latest

3 Khởi chạy Docker Container
Mở Terminal / Command Prompt / PowerShell và chạy câu lệnh sau:
- docker run -d -p 8080:8080 --name spring-container my-spring-app
