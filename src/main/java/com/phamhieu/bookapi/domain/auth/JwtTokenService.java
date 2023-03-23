package com.phamhieu.bookapi.domain.auth;

import com.phamhieu.bookapi.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.split;

@Component
@RequiredArgsConstructor
public class JwtTokenService {

    private static final Clock clock = DefaultClock.INSTANCE;

    private static final String CLAIM_ROLES = "roles";
    private static final String CLAIM_USER_ID = "userId";
    private static final String CLAIM_FIRST_NAME = "firstName";
    private static final String CLAIM_LAST_NAME = "lastName";
    private static final String CLAIM_AVATAR = "avatar";

    private final JwtProperties jwtProperties;

    public Authentication parse(final String token) {

        if (isBlank(token)) {
            return null;
        }

        final Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();

        if (isBlank(claims.getSubject())) {
            return null;
        }

        if (claims.getExpiration().before(clock.now())) {
            return null;
        }

        final String claimId = claims.get(CLAIM_USER_ID, String.class);
        if (isBlank(claimId)) {
            return null;
        }

        final String claimRoles = claims.get(CLAIM_ROLES, String.class);
        if (isBlank(claimRoles)) {
            return null;
        }

        final String claimFirstName = claims.get(CLAIM_FIRST_NAME, String.class);
        if (isBlank(claimFirstName)) {
            return null;
        }

        final String claimLastName = claims.get(CLAIM_LAST_NAME, String.class);
        if (isBlank(claimLastName)) {
            return null;
        }

        final String claimAvatar = claims.get(CLAIM_AVATAR, String.class);
        if (isBlank(claimAvatar)) {
            return null;
        }

        return new UserAuthenticationToken(
                UUID.fromString(claimId),
                claims.getSubject(),
                claimFirstName,
                claimLastName,
                claimAvatar,
                Arrays.stream(split(claimRoles, ","))
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .toList()
        );
    }

    public String generateToken(final JwtUserDetails userDetails) {
        final Date createdDate = clock.now();
        final Date expirationDate = new Date(createdDate.getTime() + jwtProperties.getExpiration() * 1000);

        final List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .claim(CLAIM_ROLES, String.join(",", roles))
                .claim(CLAIM_FIRST_NAME, userDetails.getFirstName())
                .claim(CLAIM_LAST_NAME, userDetails.getLastName())
                .claim(CLAIM_AVATAR, userDetails.getAvatar())
                .claim(CLAIM_USER_ID, userDetails.getUserId())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();
    }
}
