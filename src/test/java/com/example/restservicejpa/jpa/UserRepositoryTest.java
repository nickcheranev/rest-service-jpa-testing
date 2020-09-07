package com.example.restservicejpa.jpa;

import com.example.restservicejpa.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Cheranev N.
 * created on 05.09.2020.
 */
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    void contextTest() {
        assertThat(repository, notNullValue());
    }

    @Test
    void findAll() {
        List<User> users = repository.findAll();
        Assertions.assertNotNull(users);
        Assertions.assertEquals(users.size(), 2);
    }

    @Test
    void findByName() {
        Assertions.assertTrue(repository.findByName("guest").isPresent());
    }
}