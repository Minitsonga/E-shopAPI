package dev.minitsonga.E_shop.application.dto.Products;

import java.util.List;

public record ProductDTO(
                Long id,
                String name,
                String description,
                Double price,
                String imageUrl,
                List<String> tags) {

}
