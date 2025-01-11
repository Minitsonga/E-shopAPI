package dev.minitsonga.E_shop.service;

import dev.minitsonga.E_shop.model.Product;
import dev.minitsonga.E_shop.model.Tag;
import dev.minitsonga.E_shop.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductService
{
    private final ProductRepo productRepo;

    public ProductService (ProductRepo productRepo)
    {
        this.productRepo = productRepo;
    }

    public Product createProduct(String name, String description, String imageUrl, Double price, Integer stockQuantity, Set<Tag> tags)
    {
        return productRepo.save(new Product(name, description, imageUrl, price, stockQuantity, tags));
    }

    public Product UpdateProduct(Product product)
    {
        return productRepo.save(product);
    }

    public void deleteProduct(Long id)
    {
        productRepo.deleteById(id);
    }


    public List<Product> getAllProducts()
    {
        return productRepo.findAll();
    }


}
