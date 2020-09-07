package com.example.restservicejpa.web;

import com.example.restservicejpa.domain.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Cheranev N.
 * created on 07.09.2020.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

    final User user = User.builder().name("name").email("email").build();

    @LocalServerPort
    private int port;

    @Autowired
    private UserController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(0)
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    @Order(1)
    void findAll() {

        ResponseEntity<User[]> response = restTemplate.getForEntity("http://localhost:" + port + "/users"
                , User[].class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        User[] body = response.getBody();
        assertThat(body.length).isEqualTo(2);
        assertThat(body[0]).isEqualToIgnoringGivenFields(User.builder().name("admin"), "id", "email");
        assertThat(body[1]).isEqualToIgnoringGivenFields(User.builder().name("guest"), "id", "email");
    }

    @Test
    @Order(2)
    void create() {

        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:" + port + "/users"
                , new HttpEntity<>(user)
                , User.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        User body = response.getBody();
        assertThat(body).isEqualToIgnoringGivenFields(user, "id");
    }

    @Test
    @Order(3)
    void update() {

        User newUserValues = User.builder().name("3").email("3").build();

        ResponseEntity<User> response = restTemplate.exchange("http://localhost:" + port + "/users/2"
                , HttpMethod.PUT
                , new HttpEntity<>(newUserValues)
                , User.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        User body = response.getBody();
        assertThat(body).isEqualToIgnoringGivenFields(newUserValues, "id");
    }

    @Test
    @Order(4)
    void findById() {

        ResponseEntity<User> response = restTemplate.getForEntity("http://localhost:" + port + "/users/1"
                , User.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        User body = response.getBody();
        assertThat(body).isEqualToIgnoringGivenFields(User.builder().name("admin").email("admin@server.com").build()
                , "id");
    }

    @Test
    @Order(5)
    void findByName() {
        ResponseEntity<User> response = restTemplate.getForEntity("http://localhost:" + port + "/users/findByName?name=admin"
                , User.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        User body = response.getBody();
        assertThat(body).isEqualToIgnoringGivenFields(User.builder().name("admin").email("admin@server.com").build()
                , "id");
    }

    @Test
    @Order(6)
    void deleteById() {

        ResponseEntity<?> response = restTemplate.exchange("http://localhost:" + port + "/users/3"
                , HttpMethod.DELETE
                , new HttpEntity<>(null)
                , Object.class);

        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}