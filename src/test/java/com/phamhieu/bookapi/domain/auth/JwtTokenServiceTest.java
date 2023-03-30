package com.phamhieu.bookapi.domain.auth;

import com.phamhieu.bookapi.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static com.phamhieu.bookapi.fakes.UserFakes.buildUser;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtTokenServiceTest {
    private static final String SECRET = "bookApi";
    private static final Long EXPIRATION = 3600L;

    @Mock
    private JwtProperties jwtProperties;

    @InjectMocks
    private JwtTokenService jwtTokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtTokenService = new JwtTokenService(jwtProperties);
    }

    @Test
    void shouldParse_TokenNull() {
        final Authentication authentication = jwtTokenService.parse(null);
        assertNull(authentication);
    }

    @Test
    void generateToken_OK() {
        final var user = buildUser();
        final JwtUserDetails userDetails = new JwtUserDetails(user, List.of(new SimpleGrantedAuthority("ROLE_CONTRIBUTOR")));

        when(jwtProperties.getSecret()).thenReturn(SECRET);
        when(jwtProperties.getExpiration()).thenReturn(EXPIRATION);

        final String token = jwtTokenService.generateToken(userDetails);
        final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();

        assertEquals(user.getUsername(), claims.getSubject());
        assertEquals("ROLE_CONTRIBUTOR", claims.get("roles").toString());
        assertTrue(claims.getExpiration().after(claims.getIssuedAt()));
        assertEquals(EXPIRATION * 1000, claims.getExpiration().getTime() - claims.getIssuedAt().getTime());
    }

}