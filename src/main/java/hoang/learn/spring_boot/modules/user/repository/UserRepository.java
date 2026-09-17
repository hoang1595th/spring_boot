package hoang.learn.spring_boot.modules.user.repository;

import hoang.learn.spring_boot.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA tự động tạo câu truy vấn: SELECT * FROM users WHERE email = ?
    Optional<User> findByEmail(String email);
}
