package hoang.learn.spring_boot.modules.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name = "users")
public class User implements UserDetails {

    // Getters & Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Phù hợp với kiểu SERIAL/BIGSERIAL của PostgreSQL
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // Ví dụ: ROLE_ADMIN hoặc ROLE_STAFF

    public User() {}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Trả về danh sách quyền của User cho Spring Security
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public @Nullable String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}