package silver.backend.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Double price;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;
}
