﻿package dev.minitsonga.E_shop.repo;

import dev.minitsonga.E_shop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, String>
{
    Optional<Role> findByRole(String role);

}
