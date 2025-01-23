package dev.minitsonga.E_shop.service;

import dev.minitsonga.E_shop.domain.model.Product;
import dev.minitsonga.E_shop.repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Product createProduct(Product product) {
        return productRepo.save(product);
    }

    public Product UpdateProduct(Product product) {
        return productRepo.save(product);
    }

    public void deleteProductById(Long id) {
        if (!productRepo.existsById(id)) {
            throw new RuntimeException("Product not found with ID: " + id);
        }
        productRepo.deleteById(id);
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

}
