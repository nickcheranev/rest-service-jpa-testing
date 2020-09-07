package com.example.restservicejpa.service;

import com.example.restservicejpa.exception.ResourceNotFoundException;
import com.example.restservicejpa.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Cheranev N.
 * created on 27.08.2020.
 */
@SpringBootTest
@Transactional
public class UserServiceImplTest {

    private static final String TEST_NAME = "testname";
    private static final String UPDATE_TEST_NAME = "updatetestname";
    private static final String TEST_EMAIL = "testemail@company.com";
    private static final String UPDATE_TEST_EMAIL = "updatetestemail@company.com";
    private static final String GUEST_NAME = "guest";

    @Autowired
    private UserService userService;

    @Test
    void contextTest() {
        assertThat(userService, notNullValue());
    }

    @Test
    void findAll() {
        List<User> users = userService.findAll();
        assertThat(users, not(empty()));
    }

    @Test
    void createAndFindById() {
        User newUser = userService.create(User.builder().name(TEST_NAME).email(TEST_EMAIL).build());
        User findUser = userService.findById(newUser.getId());
        assertThat(findUser, notNullValue());
        assertThat(findUser.getName(), equalTo(TEST_NAME));
        assertThat(findUser.getEmail(), equalTo(TEST_EMAIL));
    }

    @Test
    void findByName() {
        User findUser = userService.findByName(GUEST_NAME);
        assertThat(findUser, notNullValue());
    }

    @Test
    void update() {
        User newUser = userService.create(User.builder().name(TEST_NAME).build());
        User findUser = userService.findById(newUser.getId());
        assertThat(findUser, notNullValue());
        assertThat(findUser.getEmail(), nullValue());
        findUser.setEmail(UPDATE_TEST_EMAIL);
        findUser.setName(UPDATE_TEST_NAME);
        User updateUser = userService.update(findUser.getId(), findUser);
        findUser = userService.findById(updateUser.getId());
        assertThat(findUser, notNullValue());
        assertThat(findUser.getName(), equalTo(UPDATE_TEST_NAME));
        assertThat(findUser.getEmail(), equalTo(UPDATE_TEST_EMAIL));
    }

    @Test
    void deleteById() {
        User deleteUser = userService.create(User.builder().name(TEST_NAME).email(TEST_EMAIL).build());
        userService.deleteById(deleteUser.getId());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.findById(deleteUser.getId()));
    }
}