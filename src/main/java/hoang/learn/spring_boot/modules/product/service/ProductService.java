package hoang.learn.spring_boot.modules.product.service;

import hoang.learn.spring_boot.modules.product.dto.ProductCreateRequest;
import hoang.learn.spring_boot.modules.product.dto.ProductResponse;
import hoang.learn.spring_boot.modules.product.dto.ProductUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    ProductResponse createProduct(ProductCreateRequest request);

    // Hỗ trợ tạo sản phẩm kèm upload ảnh (MultipartFile)
    ProductResponse createProductWithImage(ProductCreateRequest request, MultipartFile imageFile);

    ProductResponse getProductById(Long id);

    Page<ProductResponse> getAllProducts(String searchKeyword, Pageable pageable);

    ProductResponse updateProduct(Long id, ProductUpdateRequest request);

    void deleteProduct(Long id);

}
