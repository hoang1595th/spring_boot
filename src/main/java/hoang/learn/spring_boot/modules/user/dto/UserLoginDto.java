package hoang.learn.spring_boot.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {
    @NotBlank(message = "username can not be blank")
    private String username;
    @NotBlank(message = "password can not be blank")
    private String password;

    public UserLoginDto() {}
}
