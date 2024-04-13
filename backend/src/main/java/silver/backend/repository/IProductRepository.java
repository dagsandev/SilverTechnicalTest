package silver.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import silver.backend.domain.model.ProductEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p WHERE LOWER(p.name) LIKE %:name%")
    List<ProductEntity> findByName(String name);

    @Query("SELECT p FROM ProductEntity p WHERE LOWER(p.brand) LIKE %:brand%")
    List<ProductEntity> findByBrand(String brand);
}
