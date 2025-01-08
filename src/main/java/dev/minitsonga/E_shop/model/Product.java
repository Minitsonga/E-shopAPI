package dev.minitsonga.E_shop.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
 @Table(name = "products")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private Float price;
    private Integer stockQuantity;

    @ElementCollection // Permet de stocker une collection d'éléments simples.
    @CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tags")
    private List<String> tags = new ArrayList<String>();


    // Getters et Setters
    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}
