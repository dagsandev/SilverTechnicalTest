package silver.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.backend.domain.dto.ProductDTO;
import silver.backend.domain.model.ProductEntity;
import silver.backend.exception.ResourceNotFoundException;
import silver.backend.mapper.ProductMapper;
import silver.backend.service.IProductService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@RestController
@CrossOrigin("*")
public class ProductController {

    private final ProductMapper productMapper;
    private final IProductService productService;

    @Operation(summary = "Create product",
            description = "Save new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/")
    public ResponseEntity<Void> save (@RequestBody ProductDTO productDTO){
        if(productDTO == null){
            throw new IllegalArgumentException("Product request cannot be null");
        }
        ProductEntity product = productMapper.dtoToEntity(productDTO);

        productService.saveProduct(product);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get product by id",
            description = "Find a product and throw an exception if it was not found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation Ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id){
        ProductEntity entity = productService.findById(id);

        if(entity == null){
            throw new ResourceNotFoundException("Product request", id);
        }

        var dtoResponse = productMapper.entityToDto(entity);

        return ResponseEntity.ok(dtoResponse);
    }

    @Operation(summary = "Get a list of products",
            description = "Find a list of products and throw an exception if the list is empty.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation Ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "List of products not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> findAll(){
        var products = productService.findAll();

        if(products.isEmpty()){
            throw new ResourceNotFoundException("Product list");
        }else{
            return ResponseEntity.ok(products);
        }

    }

    @Operation(summary = "Get product by name",
            description = "Find a product and throw an exception if it was not found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation Ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/name")
    public ResponseEntity<List<ProductDTO>> findByName(@RequestParam String name){
        var dto = productService.findByName(name);

        if(dto.isEmpty()){
            throw new ResourceNotFoundException("Product request by name");
        }else{
            return ResponseEntity.ok(dto);
        }
    }

    @Operation(summary = "Get product by brand",
            description = "Find a product and throw an exception if it was not found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation Ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/brand")
    public ResponseEntity<List<ProductDTO>> findByBrand(@RequestParam String brand){
        var dto = productService.findByBrand(brand);

        if(dto.isEmpty()){
            throw new ResourceNotFoundException("Product request by brand");
        }else{
            return ResponseEntity.ok(dto);
        }
    }

    @Operation(summary = "Update product",
            description = "Update a product by locating it using its ID. If the product is not found, an exception is thrown.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation Ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long id){

        if(id == null){
            throw new IllegalArgumentException("Product id request cannot be null");
        }

        ProductEntity productEntity = productMapper.dtoToEntity(productDTO);

        productService.updateProduct(productEntity, id);

        var dtoResponse = productMapper.entityToDto(productEntity);

        return ResponseEntity.ok(dtoResponse);
    }

    @Operation(summary = "Delete product by ID",
            description = "Delete a product with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Operation successful, but no content is available to display", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct (@PathVariable Long id){

        if(id == null ){
            throw new IllegalArgumentException("Id cannot be null");
        }
        productService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
