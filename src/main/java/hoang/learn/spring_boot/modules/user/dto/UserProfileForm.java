package hoang.learn.spring_boot.modules.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UserProfileForm {
    private String name;
    private String email;
    private MultipartFile avatar; // Upload file dễ dàng

    // Bắt buộc phải có Default Constructor và các Getter/Setter
    public UserProfileForm() {}

}
