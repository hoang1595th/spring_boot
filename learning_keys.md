## (Spring Terminologies)
IoC  
DI  
Application Context  
Bean  
build.gradle  

## (Annotations)
@Controller, @RestController  
@RequestMapping, @GetMapping, @PostMapping, @Autowired, ....  
@RequestParam, @PathVariable @RequestBody, @ModelAttribute, @RequestPart, @PreAuthorize  
@Valid, validation annotations (@NotBlank, @Email....)  
@Component

## Entity
@Entity, @Table, @Index, @Getter, @Setter  
@Id, @GeneratedVAlue, @Column, @OneToMany, @ManyToOne, @PrePersit, @PreUpdate, @Override, @Nullable...  

## Main tasks
1. ### Giai đoạn 1: Thiết kế $ Kiến trúc cốt lõi
- Thiết kế Cơ sở dữ liệu & Entity
  - Ánh xạ các Entity Product và Category với các mối quan hệ JPA như @ManyToOne, @OneToMany.  
  - Đánh chỉ mục cơ sở dữ liệu (Database Indexing) cho các cột hay dùng để tìm kiếm (như tên sản phẩm) để tối ưu tốc độ truy vấn.  
- Xây dựng DTO & Validation
  - Tạo các lớp DTO (Request/Response) tách biệt với Entity, áp dụng công cụ ánh xạ dữ liệu như MapStruct hoặc ModelMapper.  
  - Kiểm tra tính hợp lệ dữ liệu đầu vào bằng các annotation validation trên DTO như @Valid, @NotNull, @NotBlank, @Size.  
- Phát triển REST APIs & Logic Nghiệp vụ
  - Xây dựng các API CRUD cho Sản phẩm và Danh mục.  
  - Hỗ trợ các tính năng tìm kiếm (Search), phân trang (Pagination) và sắp xếp (Sorting) dữ liệu sản phẩm bằng Spring Data JPA.  
  - Xử lý tải ảnh sản phẩm lên server thông qua MultipartFile kết hợp DTO JSON (dùng form-data) hoặc quy trình upload 2 bước.
  - Quản lý ngoại lệ tập trung qua @RestControllerAdvice và @ExceptionHandler để trả về định dạng lỗi chuẩn khi không tìm thấy sản phẩm.  
- Bảo mật & Phân quyền (Security & RBAC)
  - Phân quyền truy cập API: Cho phép khách hàng (Customer) xem/tìm kiếm sản phẩm và chỉ cấp quyền cho Admin tạo, chỉnh sửa hoặc xóa sản phẩm.  
- Tối ưu Hiệu năng & Caching
  - Khắc phục triệt để vấn đề N+1 Query khi truy vấn sản phẩm cùng danh mục bằng EntityGraph hoặc JOIN FETCH.  
  - Tích hợp Spring Cache với Redis bằng các annotation @Cacheable, @CacheEvict, @CachePut cho các danh mục và thông tin chi tiết sản phẩm được xem nhiều.  
- Tài liệu hóa & Kiểm thử (Docs & Testing)
  - Cấu hình Springdoc OpenAPI / Swagger UI để tự động tạo tài liệu kiểm thử cho các API Quản lý Sản phẩm.  
  - Viết Unit Test và Integration Test cho ProductService và ProductController bằng JUnit 5, Mockito và Testcontainers.

## Documents
Các loại tài liệu chính gồm có:  
1. Tài liệu thiết kế hệ thống (System Design & Architecture)
- Sơ đồ kiến trúc (Architecture Diagram): Mô tả tổng quan các thành phần backend, database, microservices, load balancer và dịch vụ bên thứ ba.
- Tài liệu thiết kế cơ sở dữ liệu (Database Schema Design): Sơ đồ thực thể mối quan hệ (ERD), danh sách các bảng, kiểu dữ liệu, index và chiến lược phân vùng (nếu có).
- Sơ đồ luồng dữ liệu (Data Flow / Sequence Diagram): Mô tả cách dữ liệu di chuyển giữa các service hoặc cách xử lý các nghiệp vụ phức tạp.
2. Tài liệu giao diện lập trình ứng dụng (API Documentation)
- Đặc tả API (API Specification): Mô tả chi tiết các endpoint, phương thức (GET, POST, PUT, DELETE), header, mã trạng thái (status code) và định dạng dữ liệu JSON request/response. Thường dùng các công cụ như Swagger/OpenAPI hoặc Postman collection.
3. Tài liệu vận hành và triển khai (DevOps & Deployment)
- Tài liệu hướng dẫn cài đặt (Setup Guide / README): Hướng dẫn cấu hình môi trường local, các biến môi trường (.env), cách chạy thử ứng dụng.
- Tài liệu triển khai (Deployment Guide): Quy trình đưa code lên môi trường Staging/Production, cấu hình Docker, CI/CD pipeline, và quản lý máy chủ.
4. Tài liệu kiểm thử và bảo mật (Testing & Security)
- Báo cáo kiểm thử API / Unit Test: Kết quả test độ phủ code (code coverage) hoặc danh sách kịch bản test case cho các service.
- Tài liệu bảo mật (Security & Authentication): Mô tả cách xác thực (JWT, OAuth2), phân quyền (RBAC) và các chính sách mã hóa dữ liệu.

## (Structure)
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