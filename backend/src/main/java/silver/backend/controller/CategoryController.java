package silver.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create category",
            description = "Save new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/")
    public ResponseEntity<Void> saveCategory(@RequestBody CategoryDTO categoryDTO){

        if(categoryDTO == null){
            throw new IllegalArgumentException("Category cannot be null");
        }

        var entity = categoryMapper.dtoToEntity(categoryDTO);

        categoryService.saveCategory(entity);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get category by id",
            description = "Find a category by id and throw an exception if it was not found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation Ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){
        var category = categoryService.findById(id);

        if (category == null){
            throw new ResourceNotFoundException("Category", id);
        }

        var dtoResponse = categoryMapper.entityToDto(category);

        return ResponseEntity.ok(dtoResponse);

    }

    @Operation(summary = "Get a list of categories",
            description = "Find a list of categories and throw an exception if the list is empty.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation Ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "List of products not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> findAll(){
        var category = categoryService.findAll();

        if(category.isEmpty()){
            throw new ResourceNotFoundException("Category list");
        }else{
            return ResponseEntity.ok(category);
        }
    }

    @Operation(summary = "Get category by name",
            description = "Find a category by name and throw an exception if it was not found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation Ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
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
