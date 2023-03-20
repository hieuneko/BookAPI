package com.phamhieu.bookapi.api.auth;

import com.phamhieu.bookapi.domain.auth.UserAuthenticationToken;
import com.phamhieu.bookapi.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.security.NoSuchAlgorithmException;

import static com.phamhieu.bookapi.api.auth.ProfileDTOMapper.toProfileDTO;
import static com.phamhieu.bookapi.api.auth.ProfileDTOMapper.toUser;


@RestController
@RequestMapping("api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final UserAuthenticationToken userAuthenticationToken;

    private final UserService userService;

    @Operation(summary = "Get user profile")
    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    @GetMapping
    public ProfileDTO get() {
        return ProfileDTO.builder()
                .userId(userAuthenticationToken.getUserId())
                .username(userAuthenticationToken.getUsername())
                .role(userAuthenticationToken.getRole())
                .build();
    }

    @Operation(summary = "Update user profile")
    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    @PutMapping
    public ProfileDTO update(@RequestBody ProfileDTO profileDTO) {
        return toProfileDTO(userService.update(profileDTO.getUserId(), toUser(profileDTO)));
    }
}
