package silver.backend.service;


import silver.backend.domain.dto.CategoryDTO;
import silver.backend.domain.model.CategoryEntity;

import java.util.List;

public interface ICategoryService {

    void saveCategory(CategoryEntity category);

    CategoryEntity findById(Long id);

    List<CategoryDTO> findAll();

    List<CategoryDTO> findByName(String name);
}
