package ru.sejapoe.dz1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sejapoe.dz1.dto.auth.LoginDto;
import ru.sejapoe.dz1.dto.auth.RefreshDto;
import ru.sejapoe.dz1.dto.auth.SignupDto;
import ru.sejapoe.dz1.dto.auth.TokenDto;
import ru.sejapoe.dz1.service.AuthService;

@RequestMapping("/api/v1/auth/")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginDto requestDto) {
        return mapTokens(authService.login(requestDto.username(), requestDto.password()));
    }

    @PostMapping("/signup")
    public TokenDto signUp(@RequestBody SignupDto requestDto) {
        return mapTokens(authService.register(requestDto.username(), requestDto.password()));
    }

    @PostMapping("/refresh")
    public TokenDto refresh(@RequestBody RefreshDto requestDto) {
        return mapTokens(authService.refresh(requestDto.token()));
    }

    private TokenDto mapTokens(AuthService.Tokens tokens) {
        return new TokenDto(tokens.accessToken(), tokens.refreshToken());
    }
}
