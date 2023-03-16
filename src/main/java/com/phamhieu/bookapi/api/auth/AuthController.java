package com.phamhieu.bookapi.api.auth;

import com.phamhieu.bookapi.domain.auth.JwtTokenService;
import com.phamhieu.bookapi.domain.auth.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.phamhieu.bookapi.api.auth.LoginDTOMapper.toAuthentication;

@RestController
@RequestMapping("api/v1/auths")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenService jwtTokenUtil;

    private final AuthenticationManager authenticationManager;

    @PostMapping
    public JwtTokenResponseDTO login(@RequestBody LoginDTO loginDTO) {
        final Authentication authentication = authenticationManager.authenticate(toAuthentication(loginDTO));

        return JwtTokenResponseDTO.builder()
                .token(jwtTokenUtil.generateToken((JwtUserDetails) authentication.getPrincipal()))
                .build();
    }
}
