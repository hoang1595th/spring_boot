package hoang.learn.spring_boot.modules.user.controller;

import hoang.learn.spring_boot.modules.user.dto.UserDto;
import hoang.learn.spring_boot.modules.user.dto.UserProfileForm;
import hoang.learn.spring_boot.modules.user.dto.UserRegistrationDto;
import hoang.learn.spring_boot.modules.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/create")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRegistrationDto userRequest) {
        // Spring Boot tự động chuyển dữ liệu JSON thành đối tượng userRequest
        String responseMessage = String.format("Đã tạo người dùng thành công: %s (%s), %d tuổi",
                userRequest.getUsername(),
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

    // Chỉ ADMIN mới được xem danh sách tất cả người dùng
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ADMIN hoặc chính chủ tài khoản mới được xem thông tin chi tiết
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
