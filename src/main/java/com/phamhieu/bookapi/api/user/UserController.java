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
@PreAuthorize("hasAnyRole('ADMIN')")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Find all users")
    @GetMapping
    public List<UserResponseDTO> findAll() {
        return toUserResponseDTOs(userService.findAll());
    }

    @Operation(summary = "Find user by id")
    @GetMapping("{userId}")
    public UserResponseDTO findById(@PathVariable UUID userId) {
        return toUserResponseDTO(userService.findById(userId));
    }

    @Operation(summary = "Find user by name")
    @GetMapping("search")
    public List<UserResponseDTO> find(final @RequestParam String name) {
        return toUserResponseDTOs(userService.find(name));
    }

    @Operation(summary = "Add new user")
    @PostMapping
    public UserResponseDTO create(@RequestBody UserRequestDTO userRequestDTO) throws NoSuchAlgorithmException {
        return toUserResponseDTO(userService.create(toUser(userRequestDTO)));
    }

    @Operation(summary = "Update user information")
    @PutMapping("{userId}")
    public UserResponseDTO update(@RequestBody UserRequestDTO userRequestDTO, @PathVariable(name = "userId") UUID userId) throws NoSuchAlgorithmException {
        return toUserResponseDTO(userService.update(userId, toUser(userRequestDTO)));
    }

    @Operation(summary = "Delete user by id")
    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable(name = "userId") UUID userId) {
        userService.delete(userId);
    }
}
