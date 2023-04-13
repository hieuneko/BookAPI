package com.phamhieu.bookapi.api.auth;

import com.phamhieu.bookapi.domain.auth.JwtTokenService;
import com.phamhieu.bookapi.domain.auth.JwtUserDetails;
import com.phamhieu.bookapi.domain.auth.SocialLoginService;
import io.swagger.v3.oas.annotations.Operation;
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

    private final SocialLoginService socialLoginService;

    private final AuthenticationManager authenticationManager;

    @Operation(summary = "User login into system")
    @PostMapping
    public JwtTokenResponseDTO login(@RequestBody LoginDTO loginDTO) {
        final Authentication authentication = authenticationManager.authenticate(toAuthentication(loginDTO));

        return generateToken((JwtUserDetails) authentication.getPrincipal());
    }

    @Operation(summary = "User login by google account")
    @PostMapping("/google")
    public JwtTokenResponseDTO loginGoogle(@RequestBody TokenRequestDTO tokenRequestDTO) {

        return generateToken((JwtUserDetails) socialLoginService.loginGoogle(tokenRequestDTO.getIdToken()));
    }

    @PostMapping("/facebook")
    public JwtTokenResponseDTO loginWithFacebook(final @RequestBody TokenRequestDTO tokenRequestDTO) {

        return generateToken((JwtUserDetails) socialLoginService.loginFacebook(tokenRequestDTO.getIdToken()));
    }

    private JwtTokenResponseDTO generateToken(final JwtUserDetails jwtUserDetails) {
        return JwtTokenResponseDTO.builder()
                .token(jwtTokenUtil.generateToken(jwtUserDetails))
                .build();
    }
}
