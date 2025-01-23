package dev.minitsonga.E_shop.infrastructure.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.minitsonga.E_shop.domain.Tag;

@Repository
public interface TagRepo extends JpaRepository<Tag, String>
{
}
