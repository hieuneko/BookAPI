package com.phamhieu.bookapi.api.profile;

import com.phamhieu.bookapi.api.user.UserRequestDTO;
import com.phamhieu.bookapi.api.user.UserResponseDTO;
import com.phamhieu.bookapi.domain.auth.UserAuthenticationToken;
import com.phamhieu.bookapi.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

import static com.phamhieu.bookapi.api.user.UserDTOMapper.toUser;
import static com.phamhieu.bookapi.api.user.UserDTOMapper.toUserResponseDTO;


@RestController
@RequestMapping("api/v1/profiles")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
public class ProfileController {

    private final UserAuthenticationToken userAuthenticationToken;

    private final UserService userService;

    @Operation(summary = "Get user profile")
    @GetMapping
    public UserResponseDTO get() {
        final UUID userId = userAuthenticationToken.getUserId();
        return toUserResponseDTO(userService.findById(userId));
    }

    @Operation(summary = "Update user profile")
    @PutMapping
    public UserResponseDTO update(@RequestBody UserRequestDTO userRequestDTO) {
        final UUID userId = userAuthenticationToken.getUserId();
        return toUserResponseDTO(userService.update(userId, toUser(userRequestDTO)));
    }
}
