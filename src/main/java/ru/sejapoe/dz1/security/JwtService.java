package ru.sejapoe.dz1.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sejapoe.dz1.config.JwtProperties;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtProperties jwtProperties;

    public String generateToken(
            String subject, Map<String, Object> extraClaims, Duration lifetime
    ) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(subject)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(lifetime)))
                .issuer(jwtProperties.getIssuer())
                .signWith(getSecretKey())
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(
                userDetails.getUsername(),
                Map.of(),
                Duration.ofDays(7)
        );
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(
                userDetails.getUsername(),
                Map.of(),
                Duration.ofMinutes(5)
        );
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername())
                && extractExpiration(token).after(Date.from(Instant.now()));
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(CharSequence token) {
        return getParser().parseSignedClaims(token).getPayload();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
    }

    private JwtParser getParser() {
        return Jwts.parser().verifyWith(getSecretKey()).build();
    }
}
