package dev.minitsonga.E_shop.application.dto.Products;

import java.util.List;

public record NewProductDTO(
        String name,
        String description,
        String imageUrl,
        Double price,
        Integer stockQuantity,
        List<String> tags) {

}
