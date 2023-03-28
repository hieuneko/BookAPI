package com.phamhieu.bookapi.domain.auth;


import com.phamhieu.bookapi.error.UsernameNotFoundException;
import com.phamhieu.bookapi.persistence.role.RoleStore;
import com.phamhieu.bookapi.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserStore userStore;
    private final RoleStore roleStore;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userStore.findByUsername(username)
                .map(this::buildUser)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    private User buildUser(final com.phamhieu.bookapi.domain.user.User user) {
        final String role = roleStore.findRoleName(user.getRoleId());
        return new JwtUserDetails(user,
                List.of(new SimpleGrantedAuthority(role)));
    }
}
