package hoang.learn.spring_boot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    // URL ví dụ: http://localhost:8080/products?name=iphone
    @GetMapping("/products")
    public String searchProduct(@RequestParam("name") String keyword) {
        return "Kết quả tìm kiếm cho từ khóa: " + keyword;
    }

    // Xử lý tham số không bắt buộc (Optional) và cài đặt giá trị mặc định (defaultValue)
    // URL có thể gọi: http://localhost:8080/items?page=2&limit=20
    // Hoặc gọi không truyền tham số: http://localhost:8080/items
    @GetMapping("/items")
    public String getItems(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        return "Hiển thị trang: " + page + " với số lượng item: " + limit;
    }

    // URL ví dụ: http://localhost:8080/departments/it/employees?status=active&page=1
    @GetMapping("/departments/{deptCode}/employees")
    public String getEmployeesByDept(
            @PathVariable String deptCode,
            @RequestParam(defaultValue = "active") String status,
            @RequestParam(defaultValue = "1") int page) {

        return "Lấy danh sách nhân viên phòng " + deptCode
                + " có trạng thái " + status
                + " ở trang " + page;
    }
}
