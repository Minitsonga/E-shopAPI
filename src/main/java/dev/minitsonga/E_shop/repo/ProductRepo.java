package dev.minitsonga.E_shop.repo;

import dev.minitsonga.E_shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>
{
    // Requête personnalisée pour filtrer par tags
    @Query("SELECT DISTINCT p FROM Product p JOIN p.tags t WHERE t IN :tags")
    List<Product> findProductByTags(@Param("tags") Set<Product.Tag> tags);

    void deleteProductById(Long id);

}
