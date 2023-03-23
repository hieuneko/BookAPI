package com.phamhieu.bookapi.domain.auth;

import com.phamhieu.bookapi.persistence.role.RoleStore;
import com.phamhieu.bookapi.persistence.user.UserEntity;
import com.phamhieu.bookapi.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.phamhieu.bookapi.persistence.user.UserEntityMapper.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserStore userStore;
    private final RoleStore roleStore;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userStore.findByUsername(username)
                .map(user -> buildUser(toUserEntity(user)))
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username+" not be found"));
    }

    private User buildUser(final UserEntity userEntity) {
        final String role = roleStore.findRoleName(userEntity.getRoleId());
        return new JwtUserDetails(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword(),
                userEntity.getFirstName(), userEntity.getLastName(), userEntity.getAvatar(),
                List.of(new SimpleGrantedAuthority(role)));
    }
}
