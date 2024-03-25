package ru.sejapoe.dz1.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.sejapoe.dz1.storage.StorageService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StorageController {
    private static final List<String> imageExtensions = List.of("png", "jpg", "gif", "jpeg");
    private final StorageService storageService;

    @GetMapping(value = "/images/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);

        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        var ext = FilenameUtils.getExtension(filename);

        if (ext.isBlank() || imageExtensions.stream().noneMatch(ext::equals)) {
            return ResponseEntity.badRequest().build();
        }


        MediaType mediaType = MediaType.parseMediaType("image/" + ext);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(file);
    }

}
