package com.phamhieu.bookapi.api.user;

import com.phamhieu.bookapi.api.AbstractControllerTest;
import com.phamhieu.bookapi.api.WithMockAdmin;
import com.phamhieu.bookapi.domain.auth.AuthsProvider;
import com.phamhieu.bookapi.domain.user.User;
import com.phamhieu.bookapi.domain.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.phamhieu.bookapi.api.user.UserDTOMapper.toUserResponseDTO;
import static com.phamhieu.bookapi.fakes.UserFakes.buildUser;
import static com.phamhieu.bookapi.fakes.UserFakes.buildUsers;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class UserControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/v1/users";

    @MockBean
    private UserService userService;

    @MockBean
    private AuthsProvider authsProvider;

    @BeforeEach
    void init() {
        when(authsProvider.getCurrentAuthentication())
                .thenCallRealMethod();
    }

    @Test
    @WithMockAdmin
    void shouldFindAll_OK() throws Exception {
        final var users = buildUsers();

        when(userService.findAll()).thenReturn(users);

            get(BASE_URL)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(users.size()))
                .andExpect(jsonPath("$[0].id").value(users.get(0).getId().toString()))
                .andExpect(jsonPath("$[0].username").value(users.get(0).getUsername()))
                .andExpect(jsonPath("$[0].firstName").value(users.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(users.get(0).getLastName()))
                .andExpect(jsonPath("$[0].enabled").value(users.get(0).isEnabled()))
                .andExpect(jsonPath("$[0].avatar").value(users.get(0).getAvatar()))
                .andExpect(jsonPath("$[0].roleId").value(users.get(0).getRoleId().toString()));

        verify(userService).findAll();
    }

    @Test
    @WithMockAdmin
    void shouldFindById_OK() throws Exception {
        final var user = buildUser();

        when(userService.findById(user.getId())).thenReturn(user);

        get(BASE_URL + "/" + user.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.enabled").value(user.isEnabled()))
                .andExpect(jsonPath("$.avatar").value(user.getAvatar()))
                .andExpect(jsonPath("$.roleId").value(user.getRoleId().toString()));

        verify(userService).findById(user.getId());
    }

    @Test
    @WithMockAdmin
    void shouldFind_Ok() throws Exception {
        final var user = buildUser();
        final var expected = buildUsers();

        when(userService.find(anyString())).thenReturn(expected);

        final var actual = userService.find(user.getUsername());

        get(BASE_URL + "/search?name=" + user.getUsername())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(actual.size()))
                .andExpect(jsonPath("$[0].id").value(actual.get(0).getId().toString()))
                .andExpect(jsonPath("$[0].username").value(actual.get(0).getUsername()))
                .andExpect(jsonPath("$[0].firstName").value(actual.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(actual.get(0).getLastName()))
                .andExpect(jsonPath("$[0].enabled").value(actual.get(0).isEnabled()))
                .andExpect(jsonPath("$[0].avatar").value(actual.get(0).getAvatar()))
                .andExpect(jsonPath("$[0].roleId").value(actual.get(0).getRoleId().toString()));
    }

    @Test
    @WithMockAdmin
    void shouldCreate_Ok() throws Exception {
        final var user = buildUser();

        when(userService.create(any(User.class))).thenReturn(user);

        post(BASE_URL, user)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.avatar").value(user.getAvatar()))
                .andExpect(jsonPath("$.roleId").value(user.getRoleId().toString()))
                .andExpect(jsonPath("$.enabled").value(user.isEnabled()));
    }

    @Test
    @WithMockAdmin
    void shouldUpdate_Ok() throws Exception {
        final var user = buildUser();
        final var updatedUser = buildUser();
        updatedUser.setId(user.getId());

        when(userService.update(eq(user.getId()), any(User.class))).thenReturn(updatedUser);

        put(BASE_URL + "/" + user.getId(), toUserResponseDTO(updatedUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedUser.getId().toString()))
                .andExpect(jsonPath("$.username").value(updatedUser.getUsername()))
                .andExpect(jsonPath("$.firstName").value(updatedUser.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(updatedUser.getLastName()))
                .andExpect(jsonPath("$.avatar").value(updatedUser.getAvatar()))
                .andExpect(jsonPath("$.enabled").value(updatedUser.isEnabled()))
                .andExpect(jsonPath("$.roleId").value(updatedUser.getRoleId().toString()));
    }

    @Test
    @WithMockAdmin
    void shouldDeleteById_Ok() throws Exception {
        final var user = buildUser();

        delete(BASE_URL + "/" + user.getId())
                .andExpect(status().isOk());

        verify(userService).delete(user.getId());
    }
}