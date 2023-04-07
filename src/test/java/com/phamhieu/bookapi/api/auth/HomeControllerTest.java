package com.phamhieu.bookapi.api.auth;

import com.phamhieu.bookapi.api.AbstractControllerTest;
import com.phamhieu.bookapi.api.WithMockAdmin;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(HomeController.class)
public class HomeControllerTest extends AbstractControllerTest {

    @Test
    @WithMockAdmin
    public void shouldLoginPage_OK() throws Exception {
        get("/")
                .andExpect(status().isOk())
                .andExpect(view().name("index.html"));
    }
}