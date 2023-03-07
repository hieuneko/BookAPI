package com.phamhieu.bookapi.api.user;

import com.phamhieu.bookapi.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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

    @Operation(summary = "find all user")
    @GetMapping
    public List<UserDTOResponse> findAll() {
        return toUserDTOs(userService.findAll());
    }

    @Operation(summary = "find user by id")
    @GetMapping("{userId}")
    public UserDTOResponse findUserById(@PathVariable(name = "userId") UUID userId) {
        return toUserDTOResponse(userService.findUserById(userId));
    }

    @Operation(summary = "find user by name")
    @GetMapping("search/{userName}")
    public List<UserDTOResponse> findUserByNameContain(@PathVariable(name = "userName") String userName) {
        return toUserDTOs(userService.findUserByName(userName));
    }

    @Operation(summary = "Add new user")
    @PostMapping
    public UserDTOResponse addUser(@RequestBody UserDTORequest userDTORequest) throws NoSuchAlgorithmException {
        return toUserDTOResponse(userService.addUser(toUser(userDTORequest)));
    }

    @Operation(summary = "Update user information")
    @PutMapping("{userId}")
    public UserDTOResponse updateUser(@RequestBody UserDTORequest userDTORequest, @PathVariable(name = "userId") UUID userId) throws NoSuchAlgorithmException {
        return toUserDTOResponse(userService.updateUser(toUser(userDTORequest), userId));
    }

    @Operation(summary = "Delete user by id")
    @DeleteMapping("{userId}")
    public String deleteUser(@PathVariable(name = "userId") UUID userId) {
        try{
            userService.deleteUser(userId);
            return "success";
        }
        catch (Exception e) {
            return "false";
        }
    }
}
