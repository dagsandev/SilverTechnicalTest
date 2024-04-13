package silver.backend.mapper;

import org.springframework.stereotype.Component;
import silver.backend.domain.dto.ProductDTO;
import silver.backend.domain.model.ProductEntity;

@Component
public class ProductMapper {


    public ProductEntity dtoToEntity(ProductDTO productDTO){
        return ProductEntity.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .brand(productDTO.getBrand())
                .stock(productDTO.getStock())
                .price(productDTO.getPrice())
                .categoryId(productDTO.getCategoryId())
                .build();

    }

    public ProductDTO entityToDto(ProductEntity product){
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .brand(product.getBrand())
                .stock(product.getStock())
                .price(product.getPrice())
                .categoryId(product.getCategoryId())
                .build();
    }

}
