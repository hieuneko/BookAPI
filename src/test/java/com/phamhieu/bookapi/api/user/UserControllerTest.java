package com.phamhieu.bookapi.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phamhieu.bookapi.domain.user.User;
import com.phamhieu.bookapi.domain.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.phamhieu.bookapi.api.user.UserDTOMapper.toUserResponseDTO;
import static com.phamhieu.bookapi.fakes.UserFakes.buildUser;
import static com.phamhieu.bookapi.fakes.UserFakes.buildUsers;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {

    private static final String BASE_URL = "/api/v1/users";

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldFindAll_OK() throws Exception {
        final var users = buildUsers();

        when(userService.findAll()).thenReturn(users);

        this.mvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(users.size()))
                .andExpect(jsonPath("$[0].id").value(users.get(0).getId().toString()))
                .andExpect(jsonPath("$[0].username").value(users.get(0).getUsername()))
                .andExpect(jsonPath("$[0].firstname").value(users.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastname").value(users.get(0).getLastName()))
                .andExpect(jsonPath("$[0].enabled").value(users.get(0).isEnabled()))
                .andExpect(jsonPath("$[0].avatar").value(users.get(0).getAvatar()))
                .andExpect(jsonPath("$[0].roleId").value(users.get(0).getRoleId().toString()));

        verify(userService).findAll();
    }

    @Test
    void shouldFindById_OK() throws Exception {
        final var user = buildUser();

        when(userService.findById(user.getId())).thenReturn(user);

        this.mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.firstname").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastname").value(user.getLastName()))
                .andExpect(jsonPath("$.enabled").value(user.isEnabled()))
                .andExpect(jsonPath("$.avatar").value(user.getAvatar()))
                .andExpect(jsonPath("$.roleId").value(user.getRoleId().toString()));

        verify(userService).findById(user.getId());
    }

    @Test
    void shouldFindUsersByNameContain_Ok() throws Exception {
        final var user = buildUser();
        final var expected = buildUsers();

        when(userService.find(anyString())).thenReturn(expected);

        final var actual = userService.find(user.getUsername());

        this.mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/search/" + user.getUsername()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(actual.size()))
                .andExpect(jsonPath("$[0].id").value(actual.get(0).getId().toString()))
                .andExpect(jsonPath("$[0].username").value(actual.get(0).getUsername()))
                .andExpect(jsonPath("$[0].firstname").value(actual.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastname").value(actual.get(0).getLastName()))
                .andExpect(jsonPath("$[0].enabled").value(actual.get(0).isEnabled()))
                .andExpect(jsonPath("$[0].avatar").value(actual.get(0).getAvatar()))
                .andExpect(jsonPath("$[0].roleId").value(actual.get(0).getRoleId().toString()));
    }

    @Test
    void shouldAddUser_Ok() throws Exception {
        final var user = buildUser();

        when(userService.create(any(User.class))).thenReturn(user);

        this.mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.firstname").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastname").value(user.getLastName()))
                .andExpect(jsonPath("$.avatar").value(user.getAvatar()))
                .andExpect(jsonPath("$.roleId").value(user.getRoleId().toString()))
                .andExpect(jsonPath("$.enabled").value(user.isEnabled()));
    }

    @Test
    void shouldUpdateUser_Ok() throws Exception {
        final var user = buildUser();
        final var updatedUser = buildUser();
        updatedUser.setId(user.getId());

        when(userService.update(eq(user.getId()), any(User.class))).thenReturn(updatedUser);

        this.mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(toUserResponseDTO(updatedUser))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedUser.getId().toString()))
                .andExpect(jsonPath("$.username").value(updatedUser.getUsername()))
                .andExpect(jsonPath("$.firstname").value(updatedUser.getFirstName()))
                .andExpect(jsonPath("$.lastname").value(updatedUser.getLastName()))
                .andExpect(jsonPath("$.avatar").value(updatedUser.getAvatar()))
                .andExpect(jsonPath("$.enabled").value(updatedUser.isEnabled()))
                .andExpect(jsonPath("$.roleId").value(updatedUser.getRoleId().toString()));
    }

    @Test
    void shouldDeleteById_Ok() throws Exception {
        final var user = buildUser();

        this.mvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + user.getId()))
                .andExpect(status().isOk());

        verify(userService).delete(user.getId());
    }
}