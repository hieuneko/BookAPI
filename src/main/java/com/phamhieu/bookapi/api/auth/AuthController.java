package com.phamhieu.bookapi.api.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.phamhieu.bookapi.domain.auth.FirebaseTokenService;
import com.phamhieu.bookapi.domain.auth.JwtTokenService;
import com.phamhieu.bookapi.domain.auth.JwtUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    private final FirebaseTokenService firebaseTokenService;

    private final AuthenticationManager authenticationManager;

    @Operation(summary = "User login into system")
    @PostMapping
    public JwtTokenResponseDTO login(@RequestBody LoginDTO loginDTO) {
        final Authentication authentication = authenticationManager.authenticate(toAuthentication(loginDTO));

        return JwtTokenResponseDTO.builder()
                .token(jwtTokenUtil.generateToken((JwtUserDetails) authentication.getPrincipal()))
                .build();
    }

    @Operation(summary = "User login by google account")
    @PostMapping("/google")
    public JwtTokenResponseDTO loginGoogle(@RequestBody TokenRequestDTO tokenRequestDTO) throws FirebaseAuthException {
        final FirebaseToken decodedToken = FirebaseAuth.getInstance()
                .verifyIdToken(tokenRequestDTO.getIdToken());

        return JwtTokenResponseDTO.builder()
                .token(jwtTokenUtil.generateToken((JwtUserDetails) firebaseTokenService.loginGoogle(decodedToken)))
                .build();
    }
}
