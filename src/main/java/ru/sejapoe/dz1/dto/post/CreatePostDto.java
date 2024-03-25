package ru.sejapoe.dz1.dto.post;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CreatePostDto(
        String title,
        String text,
        List<MultipartFile> files
) {
}
