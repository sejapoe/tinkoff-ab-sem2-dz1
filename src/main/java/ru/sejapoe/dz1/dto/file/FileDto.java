package ru.sejapoe.dz1.dto.file;

import java.io.Serializable;

/**
 * DTO for {@link ru.sejapoe.dz1.model.File}
 */
public record FileDto(Long id, String originalName, String storedName) implements Serializable {
}