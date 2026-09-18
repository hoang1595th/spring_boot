package hoang.learn.spring_boot.modules.user.service;

import hoang.learn.spring_boot.modules.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto getUserById(Long id);

    List<UserDto> getAllUsers();
}
