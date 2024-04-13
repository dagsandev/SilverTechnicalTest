package silver.backend.service.impl;

import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import silver.backend.domain.dto.ProductDTO;
import silver.backend.domain.model.ProductEntity;
import silver.backend.exception.ResourceNotFoundException;
import silver.backend.mapper.ProductMapper;
import silver.backend.repository.IProductRepository;
import silver.backend.service.IProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductMapper productMapper;

    private final IProductRepository productRepository;

    @Override
    public void saveProduct(ProductEntity product) {

        if(product == null){
            throw new IllegalArgumentException("Product cannot be null");
        }

        try{
            productRepository.save(product);
        } catch(Exception ex){
            throw new PersistenceException("Error saving product", ex);
        }
    }

    @Override
    public ProductEntity findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }


    @Override
    public List<ProductDTO> findAll() {
        List<ProductEntity> entityList = productRepository.findAll();

        if(!entityList.isEmpty()){
            return entityList.stream()
                    .map(productMapper::entityToDto)
                    .toList();
        } else {
            throw new ResourceNotFoundException("product list");
        }
    }

    @Override
    public List<ProductDTO> findByName(String name) {
        List<ProductEntity> entityList = productRepository.findByName(name);

        if(!entityList.isEmpty()){
            return entityList.stream()
                    .map(productMapper::entityToDto)
                    .toList();
        }else{
            throw new ResourceNotFoundException("product list");
        }
    }

    @Override
    public List<ProductDTO> findByBrand(String brand) {
        List<ProductEntity> entityList = productRepository.findByBrand(brand);

        if(!entityList.isEmpty()){
            return entityList.stream()
                    .map(productMapper::entityToDto)
                    .toList();
        }else{
            throw new ResourceNotFoundException("product list");
        }
    }

    @Override
    public ProductDTO updateProduct(ProductEntity product, Long id) {

        if(product == null || id == null){
            throw new IllegalArgumentException("Product or id cannot be null");
        }

        ProductEntity existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product was not found"));

        if (product.getName() != null) {
            existingProduct.setName(product.getName());
        }
        if (product.getStock() != null) {
            existingProduct.setStock(product.getStock());
        }
        if (product.getPrice() != null) {
            existingProduct.setPrice(product.getPrice());
        }
        if (product.getBrand() != null) {
            existingProduct.setBrand(product.getBrand());
        }
        if (product.getCategoryId() != null) {
            existingProduct.setCategoryId(product.getCategoryId());
        }

        ProductEntity updatedProduct = productRepository.save(existingProduct);

        return productMapper.entityToDto(updatedProduct);

    }

    @Override
    public void deleteById(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));

        productRepository.deleteById(id);

    }
}
