package com.phamhieu.bookapi.api.user;

import com.phamhieu.bookapi.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import static com.phamhieu.bookapi.api.user.UserDTOMapper.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Find all users")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public List<UserResponseDTO> findAll() {
        return toUserResponseDTOs(userService.findAll());
    }

    @Operation(summary = "Find user by id")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("{userId}")
    public UserResponseDTO findById(@PathVariable UUID userId) {
        return toUserResponseDTO(userService.findById(userId));
    }

    @Operation(summary = "Find user by name")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("search")
    public List<UserResponseDTO> find(final @RequestParam String name) {
        return toUserResponseDTOs(userService.find(name));
    }

    @Operation(summary = "Add new user")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public UserResponseDTO create(@RequestBody UserRequestDTO userRequestDTO) throws NoSuchAlgorithmException {
        return toUserResponseDTO(userService.create(toUser(userRequestDTO)));
    }

    @Operation(summary = "Update user information")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("{userId}")
    public UserResponseDTO update(@RequestBody UserRequestDTO userRequestDTO, @PathVariable(name = "userId") UUID userId) throws NoSuchAlgorithmException {
        return toUserResponseDTO(userService.update(userId, toUser(userRequestDTO)));
    }

    @Operation(summary = "Delete user by id")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable(name = "userId") UUID userId) {
        userService.delete(userId);
    }
}
