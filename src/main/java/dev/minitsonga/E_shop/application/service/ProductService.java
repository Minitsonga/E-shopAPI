package dev.minitsonga.E_shop.application.service;

import dev.minitsonga.E_shop.application.dto.Products.NewProductDTO;
import dev.minitsonga.E_shop.application.dto.Products.ProductDTO;
import dev.minitsonga.E_shop.domain.Product;
import dev.minitsonga.E_shop.domain.Tag;
import dev.minitsonga.E_shop.infrastructure.exceptions.ApiException;
import dev.minitsonga.E_shop.infrastructure.repo.ProductRepo;
import dev.minitsonga.E_shop.infrastructure.repo.TagRepo;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final TagRepo tagRepo;

    public ProductService(ProductRepo productRepo, TagRepo tagRepo) {
        this.productRepo = productRepo;
        this.tagRepo = tagRepo;
    }

    public Product createProduct(NewProductDTO newProductDTO) {
        Product product = new Product(newProductDTO.name(), newProductDTO.description(), newProductDTO.imageUrl(),
                newProductDTO.price(), newProductDTO.stockQuantity(),
                newProductDTO.tags().stream().map(this::findTagByName).collect(Collectors.toSet()));
        return productRepo.save(product);
    }

    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepo.findById(id).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Produit", id));
        product.setName(productDTO.name());
        product.setDescription(productDTO.description());
        product.setImageUrl(productDTO.imageUrl());
        product.setPrice(productDTO.price());
        Set<Tag> newTags = productDTO.tags().stream().map(this::findTagByName).collect(Collectors.toSet());
        product.setTags(newTags);

        return productRepo.save(product);
    }

    public void deleteProductById(Long id) {
        if (!productRepo.existsById(id)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Produit", id);
        }
        productRepo.deleteById(id);
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Tag findTagByName(String name) {
        return tagRepo.findByName(name).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Tag", name));
    }

}
