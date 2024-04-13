package silver.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.backend.domain.dto.CategoryDTO;
import silver.backend.exception.ResourceNotFoundException;
import silver.backend.mapper.CategoryMapper;
import silver.backend.service.ICategoryService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@RestController
@CrossOrigin("*")
public class CategoryController {

    private final CategoryMapper categoryMapper;

    private final ICategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<Void> saveCategory(@RequestBody CategoryDTO categoryDTO){

        if(categoryDTO == null){
            throw new IllegalArgumentException("Category cannot be null");
        }

        var entity = categoryMapper.dtoToEntity(categoryDTO);

        categoryService.saveCategory(entity);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){
        var category = categoryService.findById(id);

        if (category == null){
            throw new ResourceNotFoundException("Category", id);
        }

        var dtoResponse = categoryMapper.entityToDto(category);

        return ResponseEntity.ok(dtoResponse);

    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> findAll(){
        var category = categoryService.findAll();

        if(category.isEmpty()){
            throw new ResourceNotFoundException("Category list");
        }else{
            return ResponseEntity.ok(category);
        }
    }

    @GetMapping("/name")
    public ResponseEntity<List<CategoryDTO>> findByName(@RequestParam String name){
        var category = categoryService.findByName(name);

        if(category.isEmpty()){
            throw new ResourceNotFoundException("Category by name");
        }else{
            return ResponseEntity.ok(category);
        }
    }


}
