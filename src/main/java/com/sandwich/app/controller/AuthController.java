package com.sandwich.app.controller;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.sandwich.app.domain.dto.auth.JwtResponse;
import com.sandwich.app.domain.dto.auth.LoginRequest;
import com.sandwich.app.domain.dto.auth.SandwichUser;
import com.sandwich.app.service.auth.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.CharBuffer;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @GetMapping("/oauth2/jwks")
    public ResponseEntity<Map<String, Object>> jwks() {
        var publicKey = tokenService.getPublicKey();
        var rsaKey = new RSAKey.Builder(publicKey)
            .keyUse(KeyUse.SIGNATURE)
            .algorithm(JWSAlgorithm.RS256)
            .build();

        return ResponseEntity.ok(new JWKSet(rsaKey).toJSONObject());
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getLogin(), CharBuffer.wrap(request.getPassword())));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var userDetails = (SandwichUser) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse()
            .setToken(tokenService.generateJwtToken(userDetails)));
    }
}
