package hoang.learn.spring_boot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // Định nghĩa endpoint HTTP GET tại đường dẫn gốc "/"
    @GetMapping("/")
    public String sayHello() {
        return "Hello World!";
    }

    // Bạn cũng có thể định nghĩa thêm một endpoint khác như "/hello"
    @GetMapping("/hello")
    public String sayHelloCustom() {
        return "Hello World từ Spring Boot REST Controller!";
    }
}