package hoang.learn.spring_boot.modules.user.dto;

import org.springframework.web.multipart.MultipartFile;

public class UserProfileForm {
    private String name;
    private String email;
    private MultipartFile avatar; // Upload file dễ dàng

    // Bắt buộc phải có Default Constructor và các Getter/Setter
    public UserProfileForm() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public MultipartFile getAvatar() { return avatar; }
    public void setAvatar(MultipartFile avatar) { this.avatar = avatar; }
}
