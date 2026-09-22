package hoang.learn.spring_boot.modules.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hoang.learn.spring_boot.modules.product.dto.ProductCreateRequest;
import hoang.learn.spring_boot.modules.product.entity.Product;
import hoang.learn.spring_boot.modules.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class ProductControllerIntegrationTest {

    // 1. Khởi tạo Container PostgreSQL thật cho Integration Test
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    // 2. Ghi đè cấu hình datasource của Spring bằng kết nối từ Testcontainers
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll(); // Làm sạch database trước mỗi test case
    }

    @Test
    @DisplayName("GET /api/v1/products - Khách hàng chưa đăng nhập vẫn có thể xem danh sách sản phẩm")
    void getAllProducts_PublicAccess_Returns200OK() throws Exception {
        // Given: Chuẩn bị 1 sản phẩm mẫu trong DB
        Product product = new Product();
        product.setName("iPhone 15");
        product.setPrice(BigDecimal.valueOf(1200.0));
        productRepository.save(product);

        // When & Then
        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is("iPhone 15")));
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    @DisplayName("POST /api/v1/products - Role CUSTOMER tạo sản phẩm sẽ bị từ chối (403 Forbidden)")
    void createProduct_WithCustomerRole_Returns403Forbidden() throws Exception {
        ProductCreateRequest request = new ProductCreateRequest();
        request.setName("MacBook Pro");
        request.setPrice(BigDecimal.valueOf(2000.0));

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST /api/v1/products - Role ADMIN tạo sản phẩm thành công (201 Created)")
    void createProduct_WithAdminRole_Returns201Created() throws Exception {
        ProductCreateRequest request = new ProductCreateRequest();
        request.setName("MacBook Pro");
        request.setPrice(BigDecimal.valueOf(2000.0));

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is("MacBook Pro")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("DELETE /api/v1/products/{id} - ADMIN xóa sản phẩm thành công (204 No Content)")
    void deleteProduct_WithAdminRole_Returns204NoContent() throws Exception {
        // Given
        Product product = new Product();
        product.setName("Mouse Logistics");
        product.setPrice(BigDecimal.valueOf(25.0));
        Product savedProduct = productRepository.save(product);

        // When & Then
        mockMvc.perform(delete("/api/v1/products/{id}", savedProduct.getId()))
                .andExpect(status().isNoContent());

        // Kiểm tra chắc chắn DB đã xóa
        org.assertj.core.api.Assertions.assertThat(productRepository.findById(savedProduct.getId())).isEmpty();
    }
}
