package com.example.restservicejpa;

import com.example.restservicejpa.jpa.UserRepository;
import com.example.restservicejpa.service.UserService;
import com.example.restservicejpa.web.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Cheranev N.
 * created on 07.09.2020.
 */
@SpringBootTest
class RestServiceJpaAppTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        assertNotNull(context);
        UserRepository userRepository = context.getBean(UserRepository.class);
        assertNotNull(userRepository);
        UserService userService = context.getBean(UserService.class);
        assertNotNull(userService);
        UserController userController = context.getBean(UserController.class);
        assertNotNull(userController);
    }
}