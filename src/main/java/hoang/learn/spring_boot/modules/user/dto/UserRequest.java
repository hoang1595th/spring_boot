package hoang.learn.spring_boot.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class UserRequest {
    @NotBlank(message = "Tên không được để trống")
    private String name;

    @Email(message = "Email không hợp lệ")
    private String email;

    @Min(value = 18, message = "Tuổi phải từ 18 trở lên")
    private int age;

    // Default Constructor (bắt buộc phải có để Jackson làm việc)
    public UserRequest() {}

    public UserRequest(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    // Getter và Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
