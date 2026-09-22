package hoang.learn.spring_boot.modules.product.service;

import hoang.learn.spring_boot.common.exception.ResourceNotFoundException;
import hoang.learn.spring_boot.modules.product.dto.ProductCreateRequest;
import hoang.learn.spring_boot.modules.product.dto.ProductResponse;
import hoang.learn.spring_boot.modules.product.entity.Category;
import hoang.learn.spring_boot.modules.product.entity.Product;
import hoang.learn.spring_boot.modules.product.mapper.ProductMapper;
import hoang.learn.spring_boot.modules.product.repository.CategoryRepository;
import hoang.learn.spring_boot.modules.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private Category category;
    private ProductResponse productResponse;
    private ProductCreateRequest createRequest;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Điện tử");

        product = new Product();
        product.setId(1L);
        product.setName("Laptop Dell");
        product.setPrice(BigDecimal.valueOf(1500.0));
        product.setCategory(category);

        productResponse = new ProductResponse();
        productResponse.setId(1L);
        productResponse.setName("Laptop Dell");
        productResponse.setPrice(BigDecimal.valueOf(1500.0));
        productResponse.setCategoryId(1L);

        createRequest = new ProductCreateRequest();
        createRequest.setName("Laptop Dell");
        createRequest.setSku("LAP-DELL-001");
        createRequest.setPrice(BigDecimal.valueOf(1500.0));
        createRequest.setStockQuantity(10);
        createRequest.setCategoryId(1L);
    }

    @Test
    @DisplayName("Lấy chi tiết sản phẩm thành công khi ID tồn tại")
    void getProductById_WhenIdExists_ShouldReturnProductResponse() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toResponse(product)).thenReturn(productResponse);

        // When
        ProductResponse result = productService.getProductById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Laptop Dell");

        verify(productRepository, times(1)).findById(1L);
        verify(productMapper, times(1)).toResponse(product);
    }

    @Test
    @DisplayName("Ném lỗi ResourceNotFoundException khi không tìm thấy sản phẩm")
    void getProductById_WhenIdDoesNotExist_ShouldThrowException() {
        // Given
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> productService.getProductById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Product not found");

        verify(productRepository, times(1)).findById(99L);
        verifyNoInteractions(productMapper);
    }

    @Test
    @DisplayName("Tạo mới sản phẩm thành công")
    void createProduct_ValidRequest_ShouldReturnProductResponse() {
        // Given
        when(productRepository.existsBySku(createRequest.getSku())).thenReturn(false);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category)); // Giả lập tìm thấy Category
        when(productMapper.toEntity(createRequest, category)).thenReturn(product); // 3. Đã bổ sung tham số category
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toResponse(product)).thenReturn(productResponse);

        // When
        ProductResponse result = productService.createProduct(createRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Laptop Dell");
        assertThat(result.getCategoryId()).isEqualTo(1L);

        verify(categoryRepository, times(1)).findById(1L);
        verify(productMapper, times(1)).toEntity(createRequest, category);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    @DisplayName("Tạo mới sản phẩm thất bại khi không tìm thấy Category")
    void createProduct_CategoryNotFound_ShouldThrowException() {
        // Given
        when(productRepository.existsBySku(createRequest.getSku())).thenReturn(false);
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> productService.createProduct(createRequest))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(categoryRepository, times(1)).findById(1L);
        verifyNoInteractions(productMapper); // Đảm bảo mapper không được gọi nếu lỗi ở bước tìm Category
    }
}