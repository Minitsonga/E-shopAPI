package dev.minitsonga.E_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.minitsonga.E_shop.domain.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long>
{
    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    List<User> getUserByFirstNameAndLastName(String firstName, String lastName);


}
