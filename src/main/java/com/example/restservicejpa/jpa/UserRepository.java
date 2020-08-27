package com.example.restservicejpa.jpa;

import com.example.restservicejpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Cheranev N.
 * created on 20.08.2020.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    Optional<User> findByName(String name);
}
