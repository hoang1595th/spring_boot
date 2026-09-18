package hoang.learn.spring_boot.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRegistrationDto {
    // Getter và Setter
    @NotBlank(message = "Tên không được để trống")
    private String username;

    @Email(message = "Email không hợp lệ")
    private String email;

    @Min(value = 18, message = "Tuổi phải từ 18 trở lên")
    private int age;

    // Default Constructor (bắt buộc phải có để Jackson làm việc)
    public UserRegistrationDto() {}

    public UserRegistrationDto(String username, String email, int age) {
        this.username = username;
        this.email = email;
        this.age = age;
    }

}
