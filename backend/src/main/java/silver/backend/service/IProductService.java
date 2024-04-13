package silver.backend.service;

import silver.backend.domain.dto.ProductDTO;
import silver.backend.domain.model.ProductEntity;

import java.util.List;

public interface IProductService {

    void saveProduct(ProductEntity product);

    ProductEntity findById(Long id);

    List<ProductDTO> findAll();

    List<ProductDTO> findByName(String name);

    List<ProductDTO> findByBrand(String brand);

    ProductDTO updateProduct(ProductEntity product, Long id);

    void deleteById(Long id);
}
