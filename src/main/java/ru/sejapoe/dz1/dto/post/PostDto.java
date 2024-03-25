package ru.sejapoe.dz1.dto.post;

import ru.sejapoe.dz1.dto.file.FileDto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link ru.sejapoe.dz1.model.Post}
 */
public record PostDto(Long id, String title, String text, List<FileDto> images) implements Serializable {
}