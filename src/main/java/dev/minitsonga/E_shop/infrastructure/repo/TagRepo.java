package dev.minitsonga.E_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.minitsonga.E_shop.domain.model.Tag;

@Repository
public interface TagRepo extends JpaRepository<Tag, String>
{
}
