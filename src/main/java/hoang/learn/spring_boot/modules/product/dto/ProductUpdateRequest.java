package hoang.learn.spring_boot.modules.product.dto;

import hoang.learn.spring_boot.modules.product.entity.ProductStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductUpdateRequest {

    // Getters and Setters
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 150, message = "Tên sản phẩm không được vượt quá 150 ký tự")
    private String name;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Positive(message = "Giá sản phẩm phải lớn hơn 0")
    private BigDecimal price;

    @NotNull(message = "Số lượng tồn kho không được để trống")
    @Min(value = 0, message = "Số lượng tồn kho không được là số âm")
    private Integer stockQuantity;

    private String description;

    private String imageUrl;

    private ProductStatus status;

    @NotNull(message = "ID danh mục không được để trống")
    private Long categoryId;

    public ProductUpdateRequest() {}

}
