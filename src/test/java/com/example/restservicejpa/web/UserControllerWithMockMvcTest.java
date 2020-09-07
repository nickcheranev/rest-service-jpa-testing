package com.example.restservicejpa.web;

import com.example.restservicejpa.domain.User;
import com.example.restservicejpa.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Cheranev N.
 * created on 07.09.2020.
 */
@WebMvcTest(UserController.class)

// Без этой аннотации - 404 на любой запрос в тестах.
// Не смог победить
@Import(UserController.class)

class UserControllerWithMockMvcTest {

    final User user = User.builder().name("name").email("email").build();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    void findAll() throws Exception {

        String json = new ObjectMapper().writeValueAsString(Collections.singletonList(user));

        when(service.findAll()).thenReturn(Collections.singletonList(user));

        this.mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    void findById() throws Exception {

        when(service.findById(1L)).thenReturn(user);

        this.mockMvc.perform(get("/users/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(user)));
    }

    @Test
    void findByName() throws Exception {

        when(service.findByName("name")).thenReturn(user);

        this.mockMvc.perform(get("/users/findByName")
                .param("name", "name"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(user)));
    }

    @Test
    void create() throws Exception {

        final String json = new ObjectMapper().writeValueAsString(user);

        when(service.create(user)).thenReturn(user);

        this.mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(json));
    }

    @Test
    void update() throws Exception {

        final String json = new ObjectMapper().writeValueAsString(user);

        when(service.update(1L, user)).thenReturn(user);

        this.mockMvc.perform(put("/users/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    void deleteById() throws Exception {

        this.mockMvc.perform(delete("/users/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // заглушка, чтобы не грузила бин репозитория
    @Configuration
    static class TestConfigForSkipLoadJpaRepository {
    }
}
