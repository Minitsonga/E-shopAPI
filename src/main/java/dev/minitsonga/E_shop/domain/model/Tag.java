package dev.minitsonga.E_shop.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tags")
public class Tag
{
    @Id
    @Column(nullable = false, unique = true)
    private String name;


    public Tag()
    {
    }

    public Tag(String name)
    {
        this.name = name;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
