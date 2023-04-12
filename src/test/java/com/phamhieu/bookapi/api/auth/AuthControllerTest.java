package com.phamhieu.bookapi.api.auth;

import com.phamhieu.bookapi.api.AbstractControllerTest;
import com.phamhieu.bookapi.domain.auth.JwtTokenService;
import com.phamhieu.bookapi.domain.auth.JwtUserDetails;
import com.phamhieu.bookapi.domain.auth.SocialLoginService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static com.phamhieu.bookapi.fakes.AuthenticationFakes.buildAuthentication;
import static com.phamhieu.bookapi.fakes.UserFakes.buildUser;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/v1/auths";

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenService jwtTokenService;

    @MockBean
    private SocialLoginService socialLoginService;

    @Test
    void shouldLogin_Ok() throws Exception {
        final var auth = buildAuthentication();
        final var token = randomAlphabetic(3,10);

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(auth);
        when(jwtTokenService.generateToken((JwtUserDetails) auth.getPrincipal())).thenReturn(token);

        post(BASE_URL, auth)
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void shouldLoginGoogle_OK() throws Exception {
        final var tokenRequest = new TokenRequestDTO(randomAlphabetic(3, 10));
        final var token = randomAlphabetic(3, 10);
        final var user = buildUser();
        final JwtUserDetails userDetails = new JwtUserDetails(user, List.of(new SimpleGrantedAuthority("CONTRIBUTOR")));

        when(socialLoginService.loginGoogle(tokenRequest.getIdToken())).thenReturn(userDetails);
        when(jwtTokenService.generateToken(userDetails)).thenReturn(token);

        post("/api/v1/auths/google", tokenRequest)
                .andExpect(jsonPath("$.token").value(token));

        verify(socialLoginService).loginGoogle(tokenRequest.getIdToken());
        verify(jwtTokenService).generateToken(userDetails);
    }

    @Test
    void shouldLoginFacebook_OK() throws Exception {
        final var tokenRequest = new TokenRequestDTO(randomAlphabetic(3, 10));
        final var token = randomAlphabetic(3, 10);
        final var user = buildUser();
        final JwtUserDetails userDetails = new JwtUserDetails(user, List.of(new SimpleGrantedAuthority("CONTRIBUTOR")));

        when(socialLoginService.loginFacebook(tokenRequest.getIdToken())).thenReturn(userDetails);
        when(jwtTokenService.generateToken(userDetails)).thenReturn(token);

        post("/api/v1/auths/facebook", tokenRequest)
                .andExpect(jsonPath("$.token").value(token));

        verify(socialLoginService).loginFacebook(tokenRequest.getIdToken());
        verify(jwtTokenService).generateToken(userDetails);
    }
}