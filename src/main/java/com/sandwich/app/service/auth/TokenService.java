package com.sandwich.app.service.auth;

import com.sandwich.app.domain.dto.auth.SandwichUser;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenService {

    @Getter
    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;
    @Value("${security.jwt.expiration-ms}")
    private Duration jwtExpirationMs;

    @SneakyThrows
    public TokenService(@Value("${security.jwt.public-secret}") String publicSecret,
                        @Value("${security.jwt.private-secret}") String privateSecret) {
        var publicDecoded = decodeSecretKey(publicSecret, "-----BEGIN PUBLIC KEY-----", "-----END PUBLIC KEY-----");
        this.publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
            .generatePublic(new X509EncodedKeySpec(publicDecoded));

        var decoded = decodeSecretKey(privateSecret, "-----BEGIN PRIVATE KEY-----", "-----END PRIVATE KEY-----");
        this.privateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
            .generatePrivate(new PKCS8EncodedKeySpec(decoded));
    }

    @SneakyThrows
    public String generateJwtToken(SandwichUser sandwichUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", sandwichUser.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()));
        claims.put("sub", sandwichUser.getUsername());
        claims.put("userId", sandwichUser.getUserId());

        return Jwts.builder()
            .issuer(issuer)
            .claims(claims)
            .subject(sandwichUser.getUsername())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs.toMillis()))
            .signWith(privateKey)
            .compact();
    }

    @SneakyThrows
    public boolean validateJwtToken(String token) {
        try {
            return Jwts.parser().verifyWith(publicKey).build().isSigned(token);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT: {}", e.getMessage());
        }

        return false;
    }

    @SneakyThrows
    public String getSubject(String token) {
        return Jwts.parser().verifyWith(publicKey).build()
            .parseSignedClaims(token).getPayload()
            .getSubject();
    }

    private byte[] decodeSecretKey(String secret, String target, String target1) {
        var key = secret
            .replace(target, "")
            .replace(target1, "")
            .replaceAll("\\s", "");
        return Base64.getDecoder().decode(key);
    }
}