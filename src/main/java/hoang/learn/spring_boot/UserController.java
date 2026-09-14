package hoang.learn.spring_boot;

import hoang.learn.spring_boot.dto.UserRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    // URL ví dụ: http://localhost:8080/users/105
    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable("id") Long userId) {
        return "Lấy thông tin người dùng có ID = " + userId;
    }

    // Kết hợp nhiều PathVariable
    // URL ví dụ: http://localhost:8080/categories/books/products/45
    @GetMapping("/categories/{category}/products/{productId}")
    public String getProductDetail(
            @PathVariable String category,
            @PathVariable Long productId) {
        return "Sản phẩm ID " + productId + " thuộc danh mục " + category;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequest userRequest) {
        // Spring Boot tự động chuyển dữ liệu JSON thành đối tượng userRequest
        String responseMessage = String.format("Đã tạo người dùng thành công: %s (%s), %d tuổi",
                userRequest.getName(),
                userRequest.getEmail(),
                userRequest.getAge());

        return ResponseEntity.ok(responseMessage);
    }
}
