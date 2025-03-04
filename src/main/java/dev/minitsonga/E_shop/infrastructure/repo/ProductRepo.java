﻿package dev.minitsonga.E_shop.infrastructure.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.minitsonga.E_shop.domain.Product;
import dev.minitsonga.E_shop.domain.Tag;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>
{
    // Requête personnalisée pour filtrer par tags
    @Query("SELECT DISTINCT p FROM Product p JOIN p.tags t WHERE t.name IN :tags")
    List<Product> findProductByTags(@Param("tags") Set<Tag> tags);


}
