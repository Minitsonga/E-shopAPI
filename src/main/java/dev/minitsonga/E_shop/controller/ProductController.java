package dev.minitsonga.E_shop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.minitsonga.E_shop.domain.Product;
import dev.minitsonga.E_shop.domain.Tag;
import dev.minitsonga.E_shop.application.dto.Products.NewProductDTO;
import dev.minitsonga.E_shop.application.dto.Products.ProductDTO;
import dev.minitsonga.E_shop.application.service.ProductService;
import dev.minitsonga.E_shop.application.service.TagService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final TagService tagService;

    public ProductController(ProductService productService, TagService tagService) {
        this.productService = productService;
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping()
    public ResponseEntity<Product> addProduct(@RequestBody NewProductDTO newProductDTO) {
        return ResponseEntity.ok().body(productService.createProduct(newProductDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> upadateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        System.out.printf("Update Product : %s (%d) %n", productDTO.name(), id);
        return ResponseEntity.ok().body(productService.updateProduct(id, productDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("tags")
    public ResponseEntity<List<Tag>> getAllTags() {

        return ResponseEntity.ok(tagService.getAllTags());
    }

    @PostMapping("tags")
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {

        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.createTag(tag.getName()));
    }

    @DeleteMapping("tags/{name}")
    public ResponseEntity<Void> deleteTag(@PathVariable String name) {
        tagService.deleteTag(name);
        return ResponseEntity.noContent().build();
    }

}
