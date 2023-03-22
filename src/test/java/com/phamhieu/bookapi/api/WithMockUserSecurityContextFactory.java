package com.phamhieu.bookapi.api;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import static com.phamhieu.bookapi.fakes.UserAuthenticationTokenFakes.buildContributor;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockContributor> {

    @Override
    public SecurityContext createSecurityContext(WithMockContributor annotation) {
        final var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(buildContributor());
        return context;
    }
}
