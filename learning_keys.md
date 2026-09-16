[//]: # (Spring Terminologies)
IoC
DI
Application Context
Bean
build.gradle

[//]: # (Annotations)
@Controller, @RestController
@RequestMapping, @GetMapping, @PostMapping....
@RequestParam, @PathVariable @RequestBody, @ModelAttribute, @RequestPart
@Valid, validation annotations (@NotBlank, @Email....)





[//]: # (Structure)
my-spring-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── company/
│   │   │           └── project/
│   │   │               ├── Application.java              <-- File main khởi chạy ứng dụng Spring Boot
│   │   │               │
│   │   │               ├── config/                       <-- Cấu hình chung (Security, Swagger, CORS...)
│   │   │               │   ├── SecurityConfig.java
│   │   │               │   ├── OpenApiConfig.java
│   │   │               │   └── WebMvcConfig.java
│   │   │               │
│   │   │               ├── common/                       <-- Dùng chung toàn hệ thống
│   │   │               │   ├── exception/                <-- Xử lý lỗi toàn cục
│   │   │               │   │   ├── GlobalExceptionHandler.java
│   │   │               │   │   └── ResourceNotFoundException.java
│   │   │               │   ├── response/                 <-- Định dạng Response chuẩn (ApiResponse<T>)
│   │   │               │   │   └── ApiResponse.java
│   │   │               │   └── utils/                    <-- Utility/Helper classes
│   │   │               │
│   │   │               ├── modules/                      <-- Hoặc 'domains' / 'features'
│   │   │               │   ├── user/                     <-- Module Người dùng
│   │   │               │   │   ├── controller/           <-- REST API Endpoints
│   │   │               │   │   │   └── UserController.java
│   │   │               │   │   ├── service/              <-- Business Logic
│   │   │               │   │   │   ├── UserService.java
│   │   │               │   │   │   └── UserServiceImpl.java
│   │   │               │   │   ├── repository/           <-- Truy vấn Cơ sở dữ liệu (JPA)
│   │   │               │   │   │   └── UserRepository.java
│   │   │               │   │   ├── entity/               <-- JPA Database Entities
│   │   │               │   │   │   └── User.java
│   │   │               │   │   └── dto/                  <-- Request/Response DTOs
│   │   │               │   │       ├── UserRequest.java
│   │   │               │   │       └── UserResponse.java
│   │   │               │   │
│   │   │               │   └── product/                  <-- Module Sản phẩm
│   │   │               │       ├── controller/
│   │   │               │       ├── service/
│   │   │               │       ├── repository/
│   │   │               │       ├── entity/
│   │   │               │       └── dto/
│   │   │               │
│   │   │               └── security/                     <-- Tách riêng xử lý Auth/JWT
│   │   │                   ├── JwtTokenProvider.java
│   │   │                   ├── JwtAuthenticationFilter.java
│   │   │                   └── CustomUserDetailsService.java
│   │   │
│   │   └── resources/
│   │       ├── application.yml (hoặc application.properties) <-- File cấu hình ứng dụng
│   │       ├── application-dev.yml                        <-- Cấu hình môi trường dev
│   │       ├── application-prod.yml                       <-- Cấu hình môi trường prod
│   │       └── db/migration/                             <-- File SQL migration (Flyway / Liquibase)
│   │           └── V1__init_schema.sql
│   │
│   └── test/                                              <-- Mã nguồn kiểm thử (Unit test / Integration test)
│       └── java/
│           └── com/
│               └── company/
│                   └── project/
│                       ├── user/
│                       │   ├── UserControllerTest.java
│                       │   └── UserServiceTest.java
│                       └── ProductServiceTest.java
│
├── .gitignore
├── pom.xml (hoặc build.gradle)                            <-- Quản lý dependencies
└── README.md