package hoang.learn.spring_boot.modules.product.service;

import hoang.learn.spring_boot.modules.product.dto.ProductCreateRequest;
import hoang.learn.spring_boot.modules.product.dto.ProductResponse;
import hoang.learn.spring_boot.modules.product.dto.ProductUpdateRequest;
import hoang.learn.spring_boot.modules.product.entity.Category;
import hoang.learn.spring_boot.modules.product.entity.Product;
import hoang.learn.spring_boot.modules.product.mapper.ProductMapper;
import hoang.learn.spring_boot.modules.product.repository.CategoryRepository;
import hoang.learn.spring_boot.modules.product.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    @Transactional
    // Khi thêm mới sản phẩm, nếu bạn có cache danh sách sản phẩm thì xóa cache danh sách
    @CacheEvict(value = "products", allEntries = true)
    public ProductResponse createProduct(ProductCreateRequest request) {
        if (productRepository.existsBySku(request.getSku())) {
            throw new IllegalArgumentException("Mã SKU '" + request.getSku() + "' đã tồn tại!");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Category với ID: " + request.getCategoryId()));

        Product product = productMapper.toEntity(request, category);
        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponse createProductWithImage(ProductCreateRequest request, MultipartFile imageFile) {
        // Trong thực tế: Xử lý lưu file ảnh vào folder/Amazon S3 -> lấy imageUrl
        if (imageFile != null && !imageFile.isEmpty()) {
            String uploadedImageUrl = "/uploads/" + imageFile.getOriginalFilename();
            request.setImageUrl(uploadedImageUrl);
        }
        return createProduct(request);
    }

    @Override
    @Transactional(readOnly = true)
    // 1. @Cacheable: Kiểm tra trong Redis cache "product_detail" với key là id.
    // Nếu có -> Trả về ngay lập tức (Hit cache), không chạy vào hàm này.
    // Nếu chưa có -> Chạy truy vấn DB, lấy dữ liệu lưu vào Redis (Miss cache) rồi trả về.
    @Cacheable(value = "product_detail", key = "#id", unless = "#result == null")
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findByIdWithCategory(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + id));
        return productMapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(String searchKeyword, Pageable pageable) {
        Page<Product> products;
        if (searchKeyword != null && !searchKeyword.isBlank()) {
            products = productRepository.findByNameContainingIgnoreCase(searchKeyword, pageable);
        } else {
            products = productRepository.findAll(pageable);
        }
        return products.map(productMapper::toResponse);
    }

    @Override
    @Transactional
    // 1. @Cacheable: Kiểm tra trong Redis cache "product_detail" với key là id.
    // Nếu có -> Trả về ngay lập tức (Hit cache), không chạy vào hàm này.
    // Nếu chưa có -> Chạy truy vấn DB, lấy dữ liệu lưu vào Redis (Miss cache) rồi trả về.
    @Cacheable(value = "product_detail", key = "#id", unless = "#result == null")
    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + id));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Category với ID: " + request.getCategoryId()));

        productMapper.updateEntityFromDto(request, product, category);
        Product updatedProduct = productRepository.save(product);

        return productMapper.toResponse(updatedProduct);
    }

    @Override
    @Transactional
    // 3. @CacheEvict khi Xóa: Xóa cache chi tiết sản phẩm này và cả cache danh sách sản phẩm
    @CacheEvict(value = "product_detail", key = "#id")
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy sản phẩm với ID: " + id);
        }
        productRepository.deleteById(id);
    }
}
