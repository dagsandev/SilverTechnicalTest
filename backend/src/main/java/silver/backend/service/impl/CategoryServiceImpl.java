package silver.backend.service.impl;

import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import silver.backend.domain.dto.CategoryDTO;
import silver.backend.domain.dto.ProductDTO;
import silver.backend.domain.model.CategoryEntity;
import silver.backend.domain.model.ProductEntity;
import silver.backend.exception.ResourceNotFoundException;
import silver.backend.mapper.CategoryMapper;
import silver.backend.repository.ICategoryRepository;
import silver.backend.service.ICategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public void saveCategory(CategoryEntity category) {

        if(category == null){
            throw new IllegalArgumentException("Category cannot be null");
        }

        try{
            categoryRepository.save(category);
        } catch(Exception ex){
            throw new PersistenceException("Error saving category", ex);
        }

    }


    @Override
    public CategoryEntity findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
    }

    @Override
    public List<CategoryDTO> findAll() {
        List<CategoryEntity> entityList = categoryRepository.findAll();

        if(!entityList.isEmpty()){
            return entityList.stream()
                    .map(categoryMapper::entityToDto)
                    .toList();
        } else {
            throw new ResourceNotFoundException("Category list");
        }
    }

    @Override
    public List<CategoryDTO> findByName(String name) {
        List<CategoryEntity> entityList = categoryRepository.findByName(name);

        if(!entityList.isEmpty()){
            return entityList.stream()
                    .map(categoryMapper::entityToDto)
                    .toList();
        }else{
            throw new ResourceNotFoundException("Category list");
        }
    }
}
