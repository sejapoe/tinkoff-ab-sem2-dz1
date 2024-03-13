package ru.sejapoe.dz1.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sejapoe.dz1.exception.ConflictException;
import ru.sejapoe.dz1.exception.ForbiddenException;
import ru.sejapoe.dz1.exception.NotFoundException;
import ru.sejapoe.dz1.model.RefreshToken;
import ru.sejapoe.dz1.model.User;
import ru.sejapoe.dz1.repo.RefreshTokenRepository;
import ru.sejapoe.dz1.repo.UserRepository;
import ru.sejapoe.dz1.security.JwtService;
import ru.sejapoe.dz1.security.SecurityUtils;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationProvider authenticationProvider;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User with %s is not found".formatted(username)));
    }

    @Transactional
    public Tokens login(String username, String password) {
        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        var user = getByUsername(username);
        var tokens = generateTokenPair(user);
        var refreshToken = RefreshToken.builder().token(tokens.refreshToken).user(user).build();
        refreshTokenRepository.save(refreshToken);
        return tokens;
    }

    @Transactional
    public Tokens refresh(String token) {
        var refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new ForbiddenException("Unknown refresh token"));

        try {
            jwtService.validateToken(token, refreshToken.getUser());
        } catch (ExpiredJwtException e) {
            throw new ForbiddenException("Refresh token is expired");
        } catch (JwtException e) {
            throw new ForbiddenException("Bad refresh token");
        }

        var tokens = generateTokenPair(refreshToken.getUser());
        refreshToken.setToken(tokens.refreshToken);
        refreshTokenRepository.save(refreshToken);
        return tokens;
    }

    @Transactional
    public Tokens register(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new ConflictException("User with that username already exists!");
        }

        var user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .enabled(true)
                .role(User.Role.USER)
                .build();
        user = userRepository.save(user);

        var tokens = generateTokenPair(user);
        var refreshToken = RefreshToken.builder().token(tokens.refreshToken).user(user).build();
        refreshTokenRepository.save(refreshToken);
        return tokens;
    }

    private Tokens generateTokenPair(User user) {
        return new Tokens(
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user)
        );
    }

    public void promote() {
        var user = SecurityUtils.requireCurrentUser();
        user.setRole(User.Role.ADMIN);
        userRepository.save(user);
    }

    public record Tokens(
            String accessToken,
            String refreshToken
    ) {
    }
}
