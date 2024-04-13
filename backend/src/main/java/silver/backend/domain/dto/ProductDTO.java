package silver.backend.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {

    private Long id;

    private String name;

    private String brand;

    private Integer stock;

    private Double price;

    private Long categoryId;
}
