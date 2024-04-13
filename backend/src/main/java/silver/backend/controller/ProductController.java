package silver.backend.controller;

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

    @PostMapping("/")
    public ResponseEntity<Void> save (@RequestBody ProductDTO productDTO){
        if(productDTO == null){
            throw new IllegalArgumentException("Product request cannot be null");
        }
        ProductEntity product = productMapper.dtoToEntity(productDTO);

        productService.saveProduct(product);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id){
        ProductEntity entity = productService.findById(id);

        if(entity == null){
            throw new ResourceNotFoundException("Product request", id);
        }

        var dtoResponse = productMapper.entityToDto(entity);

        return ResponseEntity.ok(dtoResponse);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> findAll(){
        var products = productService.findAll();

        if(products.isEmpty()){
            throw new ResourceNotFoundException("Product list");
        }else{
            return ResponseEntity.ok(products);
        }

    }

    @GetMapping("/name")
    public ResponseEntity<List<ProductDTO>> findByName(@RequestParam String name){
        var dto = productService.findByName(name);

        if(dto.isEmpty()){
            throw new ResourceNotFoundException("Product request by name");
        }else{
            return ResponseEntity.ok(dto);
        }
    }

    @GetMapping("/brand")
    public ResponseEntity<List<ProductDTO>> findByBrand(@RequestParam String brand){
        var dto = productService.findByBrand(brand);

        if(dto.isEmpty()){
            throw new ResourceNotFoundException("Product request by brand");
        }else{
            return ResponseEntity.ok(dto);
        }
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct (@PathVariable Long id){

        if(id == null ){
            throw new IllegalArgumentException("Id cannot be null");
        }
        productService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
