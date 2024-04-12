package ru.sejapoe.dz1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sejapoe.dz1.service.AuthService;

@RequestMapping("/api/v1/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;

    @Profile("dev")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/promote")
    public void promote() {
        authService.promote();
    }
}
