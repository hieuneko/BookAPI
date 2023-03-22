package com.phamhieu.bookapi.api.profile;

import com.phamhieu.bookapi.domain.profile.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import static com.phamhieu.bookapi.api.profile.ProfileDTOMapper.toProfileResponseDTO;
import static com.phamhieu.bookapi.api.profile.ProfileDTOMapper.toUser;


@RestController
@RequestMapping("api/v1/profiles")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
public class ProfileController {


    private final ProfileService profileService;

    @Operation(summary = "Find user profile")
    @GetMapping
    public ProfileResponseDTO find() {
        return toProfileResponseDTO(profileService.findProfile());
    }

    @Operation(summary = "Update user profile")
    @PutMapping
    public ProfileResponseDTO update(@RequestBody ProfileRequestDTO profileRequestDTO) {
        return toProfileResponseDTO(profileService.update(toUser(profileRequestDTO)));
    }
}
