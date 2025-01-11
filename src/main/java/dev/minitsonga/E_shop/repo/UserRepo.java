﻿package dev.minitsonga.E_shop.repo;

import dev.minitsonga.E_shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long>
{
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<List<User>> findByFirstNameAndLastName(String firstName, String lastName);

}
