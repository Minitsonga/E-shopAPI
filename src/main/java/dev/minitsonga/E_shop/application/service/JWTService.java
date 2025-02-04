package dev.minitsonga.E_shop.application.service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import dev.minitsonga.E_shop.domain.RefreshToken;
import dev.minitsonga.E_shop.domain.User;
import dev.minitsonga.E_shop.infrastructure.repo.RefreshTokenRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JWTService {

    private final SecretKey secretKey;

    private final RefreshTokenRepo refreshTokenRepo;

    public JWTService(RefreshTokenRepo refreshTokenRepo) {
        this.refreshTokenRepo = refreshTokenRepo;
        this.secretKey = generateSecretKey();
    }

    public SecretKey generateSecretKey() {
        try {
            // Génère une clé HMAC de 256 bits
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            keyGenerator.init(256);
            return keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate secret key", e);
        }
    }

    public String generateAccessToken(User user) {

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("roles", user.getRoles());
        return buildToken(claims, user, 1000L * 60 * 15);
    }

    public RefreshToken generateRefreshToken(User user) {

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("roles", user.getRoles());
        String token = buildToken(claims, user, 1000L * 60 * 60 * 24 * 7);
        RefreshToken refreshToken = new RefreshToken(token, Instant.now().plusMillis(1000L * 60 * 60 * 24 * 7), user);
        return refreshTokenRepo.save(refreshToken);
    }

    private String buildToken(Map<String, Object> extraClaims, User user, Long expiration) {
        return Jwts.builder()
                .claims()
                .add(extraClaims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .and()
                .signWith(secretKey)
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public void revokeRefreshToken(User user) {
        refreshTokenRepo.deleteByUser(user);
    }

    public RefreshToken findRefreshToken(String token) {
        return refreshTokenRepo.findByToken(token).isPresent() ? refreshTokenRepo.findByToken(token).get() : null;
    }

}
