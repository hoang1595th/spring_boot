package hoang.learn.spring_boot.modules.product.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductCreateRequest {

    // Getters and Setters
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 150, message = "Tên sản phẩm không được vượt quá 150 ký tự")
    private String name;

    @NotBlank(message = "Mã SKU không được để trống")
    @Size(max = 50, message = "Mã SKU không được vượt quá 50 ký tự")
    private String sku;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Positive(message = "Giá sản phẩm phải lớn hơn 0")
    private BigDecimal price;

    @NotNull(message = "Số lượng tồn kho không được để trống")
    @Min(value = 0, message = "Số lượng tồn kho không được là số âm")
    private Integer stockQuantity;

    private String description;

    private String imageUrl;

    @NotNull(message = "ID danh mục không được để trống")
    private Long categoryId;

    public ProductCreateRequest() {}

}
