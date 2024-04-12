package ru.sejapoe.dz1.dto.auth;

public record TokenDto(
        String accessToken,
        String refreshToken
) {
}
