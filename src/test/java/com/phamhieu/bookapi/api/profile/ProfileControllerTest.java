package com.phamhieu.bookapi.api.profile;

import com.phamhieu.bookapi.api.AbstractControllerTest;
import com.phamhieu.bookapi.api.WithMockContributor;
import com.phamhieu.bookapi.domain.auth.AuthsProvider;
import com.phamhieu.bookapi.domain.user.User;
import com.phamhieu.bookapi.domain.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.phamhieu.bookapi.api.profile.ProfileDTOMapper.toProfileResponseDTO;
import static com.phamhieu.bookapi.fakes.UserFakes.buildUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
@AutoConfigureMockMvc
class ProfileControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/v1/profiles";

    @MockBean
    private AuthsProvider authsProvider;

    @MockBean
    private UserService userService;

    @BeforeEach
    void init() {
        when(authsProvider.getCurrentAuthentication()).thenCallRealMethod();
    }

    @Test
    @WithMockContributor
    void shouldGetProfile_OK() throws Exception {
        final var profile = buildUser();

        when(userService.findProfile())
                .thenReturn(profile);

        get(BASE_URL)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(profile.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(profile.getLastName()))
                .andExpect(jsonPath("$.avatar").value(profile.getAvatar()));

        verify(userService).findProfile();
    }

    @Test
    @WithMockContributor
    void shouldUpdate_Ok() throws Exception {
        final var profile = buildUser();
        final var updatedProfile = buildUser();
        updatedProfile.setId(profile.getId());

        when(userService.updateProfile(any(User.class))).thenReturn(updatedProfile);

        put(BASE_URL, toProfileResponseDTO(updatedProfile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(updatedProfile.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(updatedProfile.getLastName()))
                .andExpect(jsonPath("$.avatar").value(updatedProfile.getAvatar()));
    }
}