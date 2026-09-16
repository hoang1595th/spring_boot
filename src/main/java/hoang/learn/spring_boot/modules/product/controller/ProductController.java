package hoang.learn.spring_boot.modules.product.controller;

import hoang.learn.spring_boot.modules.product.dto.ProductCreateDto;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ProductController {

    // URL ví dụ: http://localhost:8080/products?name=iphone
    @GetMapping("/products")
    public String searchProduct(@RequestParam("name") String keyword) {
        return "Kết quả tìm kiếm cho từ khóa: " + keyword;
    }

    // Xử lý tham số không bắt buộc (Optional) và cài đặt giá trị mặc định (defaultValue)
    // URL có thể gọi: http://localhost:8080/items?page=2&limit=20
    // Hoặc gọi không truyền tham số: http://localhost:8080/items
    @GetMapping("/items")
    public String getItems(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        return "Hiển thị trang: " + page + " với số lượng item: " + limit;
    }

    // URL ví dụ: http://localhost:8080/departments/it/employees?status=active&page=1
    @GetMapping("/departments/{deptCode}/employees")
    public String getEmployeesByDept(
            @PathVariable String deptCode,
            @RequestParam(defaultValue = "active") String status,
            @RequestParam(defaultValue = "1") int page) {

        return "Lấy danh sách nhân viên phòng " + deptCode
                + " có trạng thái " + status
                + " ở trang " + page;
    }

    // Kết hợp nhiều PathVariable
    // URL ví dụ: http://localhost:8080/categories/books/products/45
    @GetMapping("/categories/{category}/products/{productId}")
    public String getProductDetail(
            @PathVariable String category,
            @PathVariable Long productId) {
        return "Sản phẩm ID " + productId + " thuộc danh mục " + category;
    }

    @PostMapping(value = "/product/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createProduct(
            // Part 1: Nhận chuỗi JSON và tự động giải mã thành Object + Validate
            @Valid @RequestPart("data") ProductCreateDto productDto,

            // Part 2: Nhận file upload
            @RequestPart("file") MultipartFile imageFile) {

        // Kiểm tra file có rỗng không
        if (imageFile.isEmpty()) {
            return ResponseEntity.badRequest().body("Vui lòng chọn một file ảnh!");
        }

        String result = String.format("Đã tạo sản phẩm '%s' thành công với giá %.2f. File tải lên: %s (%d bytes)",
                productDto.getName(),
                productDto.getPrice(),
                imageFile.getOriginalFilename(),
                imageFile.getSize());

        return ResponseEntity.ok(result);
    }
}
