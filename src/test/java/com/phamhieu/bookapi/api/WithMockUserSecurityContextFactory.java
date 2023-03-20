package com.phamhieu.bookapi.api;

import com.phamhieu.bookapi.domain.auth.UserAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;
import java.util.UUID;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockUser annotation) {
        final var context = SecurityContextHolder.createEmptyContext();

        final Authentication auth = new UserAuthenticationToken(
                UUID.fromString("e1606d14-39ef-4f9f-8670-0dc028436ac0"),
                "user",
                List.of(new SimpleGrantedAuthority("ROLE_CONTRIBUTOR"))
        );
        context.setAuthentication(auth);
        return context;
    }
}
