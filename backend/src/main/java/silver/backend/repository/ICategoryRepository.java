package silver.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import silver.backend.domain.model.CategoryEntity;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("SELECT c FROM CategoryEntity c WHERE LOWER(c.name) LIKE %:name%")
    List<CategoryEntity> findByName(String name);
}
