package dev.minitsonga.E_shop.repo;

import dev.minitsonga.E_shop.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepo extends JpaRepository<Tag, String>
{
}
