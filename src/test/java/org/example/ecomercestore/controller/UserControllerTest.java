package org.example.ecomercestore.controller;

import org.example.ecomercestore.dto.UserResponseDTO;
import org.example.ecomercestore.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserService userService;

    @Test
    void shouldReturnUsers() throws Exception {
        UserResponseDTO user1 = new UserResponseDTO();
        user1.setUserId(1L);
        user1.setUserName("Ion");
        user1.setEmail("ion@gmail.com");
        user1.setRole("user");

        UserResponseDTO user2 = new UserResponseDTO();
        user2.setUserId(2L);
        user2.setUserName("Mihai");
        user2.setEmail("mihai@yahoo.com");
        user2.setRole("user");

        when(userService.getAllUsers())
                .thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userName").value("Ion"))
                .andExpect(jsonPath("$[1].userName").value("Mihai"));
    }
}


