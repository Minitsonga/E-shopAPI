﻿package dev.minitsonga.E_shop.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private Integer stockQuantity;

    @ElementCollection(targetClass = Tag.class)
    @CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
    @Enumerated(EnumType.STRING) // Stocke les valeurs Enum sous forme de texte
    @Column(name = "tag")
    private Set<Tag> tags = new HashSet<>();

    // Getters et Setters
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }


    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Integer getStockQuantity()
    {
        return stockQuantity;
    }

    public void getStockQuantity(Integer stock)
    {
        this.stockQuantity = stock;
    }

    public Set<Tag> getTags()
    {
        return tags;
    }

    public void setTags(Set<Tag> tags)
    {
        this.tags = tags;
    }


    public enum Tag
    {
        HIGHTECH,
        PROMOTION,
        BESTSELLER,
        SOLDES,
        NOUVEAU,
        LIVRE,
        EPICERIE,
        CUISINE,
        SPORT
    }

}

