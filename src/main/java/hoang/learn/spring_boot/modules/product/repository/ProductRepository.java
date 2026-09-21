package hoang.learn.spring_boot.modules.product.repository;

import hoang.learn.spring_boot.modules.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Kiểm tra SKU đã tồn tại chưa khi tạo mới
    boolean existsBySku(String sku);

    // Tìm kiếm sản phẩm theo tên (có phân trang & không phân biệt hoa thường)
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Đã tối ưu truy vấn JOIN FETCH để tránh N+1 Query khi lấy chi tiết sản phẩm kèm Category
    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.id = :id")
    Optional<Product> findByIdWithCategory(@Param("id") Long id);

     @Query("SELECT p FROM Product p WHERE p.price < :maxPrice")
     List<Product> findProductsCheaperThan(@Param("maxPrice") Double maxPrice);
}