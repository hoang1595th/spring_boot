package hoang.learn.spring_boot.modules.product.controller;

import hoang.learn.spring_boot.modules.product.dto.ProductCreateRequest;
import hoang.learn.spring_boot.modules.product.dto.ProductResponse;
import hoang.learn.spring_boot.modules.product.dto.ProductUpdateRequest;
import hoang.learn.spring_boot.modules.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 1. API Tạo mới sản phẩm (JSON thuần)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2. API Tạo mới sản phẩm kèm Upload Ảnh (Multipart Form Data - @RequestPart)
    @PostMapping(value = "/with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProductWithImage(
            @Valid @RequestPart("data") ProductCreateRequest request,
            @RequestPart(value = "file", required = false) MultipartFile imageFile) {
        ProductResponse response = productService.createProductWithImage(request, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 3. API Lấy chi tiết sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // 4. API Lấy danh sách sản phẩm (Hỗ trợ Search, Pagination & Sorting)
    // Ví dụ URL: GET /api/products?keyword=laptop&page=0&size=10&sort=price,desc
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(required = false) String keyword,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(keyword, pageable));
    }

    // 5. API Cập nhật sản phẩm
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    // 6. API Xóa sản phẩm
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
