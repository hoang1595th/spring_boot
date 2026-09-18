package hoang.learn.spring_boot.modules.user.service;

import hoang.learn.spring_boot.common.exception.ResourceNotFoundException;
import hoang.learn.spring_boot.modules.user.dto.UserDto;
import hoang.learn.spring_boot.modules.user.entity.User;
import hoang.learn.spring_boot.modules.user.mapper.UserMapper;
import hoang.learn.spring_boot.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Thêm mới user vào PostgreSQL
    public User createUser(String fullName, String email) {
        User user = new User(fullName, email);
        return userRepository.save(user);
    }

    // Lấy toàn bộ danh sách user
    public List<UserDto> getAllUsers() {
        UserMapper mapper = new UserMapper();
        List<User> userList = userRepository.findAll();
        List<UserDto> users = new ArrayList<>();

        for (User user : userList) {
            users.add(mapper.toDto(user));
        }

        return users;
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        UserMapper mapper = new UserMapper();
        return mapper.toDto(user);
    }
}
