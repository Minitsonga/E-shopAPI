package dev.minitsonga.E_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.minitsonga.E_shop.domain.model.Role;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, String>
{
    Optional<Role> findByRole(String role);

}
