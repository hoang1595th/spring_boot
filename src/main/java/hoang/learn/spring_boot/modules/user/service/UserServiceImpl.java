package hoang.learn.spring_boot.modules.user.service;

import hoang.learn.spring_boot.modules.user.entity.User;
import hoang.learn.spring_boot.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl {

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
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
