package ru.sejapoe.dz1.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.sejapoe.dz1.model.File;

/**
 * The StorageService interface provides methods for initializing, storing, loading, and deleting files from a storage.
 */
public interface StorageService {
    /**
     * Initializes the storage service.
     */
    void init();

    /**
     * Stores the given file in the storage.
     *
     * @param file the file to be stored
     * @return stored document entity
     */
    File store(MultipartFile file);

    /**
     * Loads the file with the specified filename from the storage and returns it as a Resource object.
     *
     * @param filename the name of the file to be loaded
     * @return the loaded file as a Resource object
     */
    Resource loadAsResource(String filename);

    /**
     * Returns the original name of the file without any modifications.
     *
     * @param filename the name of the file
     * @return the original name of the file
     */
    String getOriginalName(String filename);

    /**
     * Deletes all files and directories from the storage.
     * <p>
     * This method deletes all files and directories from the storage.
     * </p>
     */
    void deleteAll();
}