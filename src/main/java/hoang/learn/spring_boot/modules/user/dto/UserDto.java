package hoang.learn.spring_boot.modules.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    private Long id;
    private String username;
    private String email;

    public UserDto() {}
}
