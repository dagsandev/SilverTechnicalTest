package silver.backend.mapper;

import org.springframework.stereotype.Component;
import silver.backend.domain.dto.CategoryDTO;
import silver.backend.domain.model.CategoryEntity;

@Component
public class CategoryMapper {

    public CategoryEntity dtoToEntity(CategoryDTO categoryDTO){
        return CategoryEntity.builder()
                .name(categoryDTO.getName())
                .build();

    }

    public CategoryDTO entityToDto(CategoryEntity category){
        return CategoryDTO.builder()
                .name(category.getName())
                .build();
    }
}
