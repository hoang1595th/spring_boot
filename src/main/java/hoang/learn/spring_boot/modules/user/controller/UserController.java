package hoang.learn.spring_boot.modules.user.controller;

import hoang.learn.spring_boot.modules.user.dto.UserProfileForm;
import hoang.learn.spring_boot.modules.user.dto.UserRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController {

    // URL ví dụ: http://localhost:8080/users/105
    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable("id") Long userId) {
        return "Lấy thông tin người dùng có ID = " + userId;
    }

    @PostMapping("/users/create")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequest userRequest) {
        // Spring Boot tự động chuyển dữ liệu JSON thành đối tượng userRequest
        String responseMessage = String.format("Đã tạo người dùng thành công: %s (%s), %d tuổi",
                userRequest.getName(),
                userRequest.getEmail(),
                userRequest.getAge());

        return ResponseEntity.ok(responseMessage);
    }

    @PostMapping("/users/profile")
    public ResponseEntity<String> updateProfile(@ModelAttribute UserProfileForm form) {
        // Spring tự động đọc name, email từ form-data và gán vào form object
        MultipartFile avatar = form.getAvatar();
        return ResponseEntity.ok("Cập nhật thành công cho: " + form.getName());
    }
}
