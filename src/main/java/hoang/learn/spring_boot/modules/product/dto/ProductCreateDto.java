package hoang.learn.spring_boot.modules.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductCreateDto {

    // Getters và Setters
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    @NotNull(message = "Giá sản phẩm không được để trống")
    private Double price;

    private String description;

    // Default Constructor (bắt buộc)
    public ProductCreateDto() {}

}
