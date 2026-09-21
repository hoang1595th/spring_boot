package hoang.learn.spring_boot.modules.product.mapper;

import hoang.learn.spring_boot.modules.product.dto.ProductCreateRequest;
import hoang.learn.spring_boot.modules.product.dto.ProductResponse;
import hoang.learn.spring_boot.modules.product.dto.ProductUpdateRequest;
import hoang.learn.spring_boot.modules.product.entity.Category;
import hoang.learn.spring_boot.modules.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring", // Khai báo để Spring coi MapStruct Mapper là một Spring Bean (@Component)
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE // Bỏ qua các field null khi update
)
public interface ProductMapper {

    // 1. Chuyển từ Request sang Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "category")
    @Mapping(target = "status", ignore = true) // Mặc định trong Entity đã tự set ACTIVE
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toEntity(ProductCreateRequest request, Category category);

    // 2. Chuyển từ Entity sang Response DTO
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    ProductResponse toResponse(Product product);

    // 3. Cập nhật dữ liệu từ DTO vào Entity hiện có (@MappingTarget)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sku", ignore = true) // Không cho cập nhật SKU
    @Mapping(target = "category", source = "category")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(ProductUpdateRequest request, @MappingTarget Product product, Category category);
}
