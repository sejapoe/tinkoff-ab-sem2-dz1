package ru.sejapoe.dz1.storage;

import com.google.common.collect.Streams;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.MinioException;
import io.minio.messages.DeleteObject;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sejapoe.dz1.config.StorageProperties;
import ru.sejapoe.dz1.exception.NotFoundException;
import ru.sejapoe.dz1.exception.StorageException;
import ru.sejapoe.dz1.model.File;
import ru.sejapoe.dz1.repo.FileRepository;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3StorageService implements StorageService {
    private final StorageProperties storageProperties;
    private final MinioClient minioClient;
    private final FileRepository documentRepository;
    private String bucketName;

    @SneakyThrows
    @PostConstruct
    @Override
    public void init() {
        bucketName = storageProperties.getBucketName();

        if (Objects.isNull(bucketName) || bucketName.isBlank()) {
            throw new StorageException("You should specify bucket name to use S3 storage");
        }

        boolean doesBucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!doesBucketExists) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build()
            );
//            throw new StorageException("Bucket with given name does not exists in S3");
        }

        log.info("S3 storage has successfully initialized");
    }

    @SneakyThrows
    @Override
    public File store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file");
            }

            String filename = file.getOriginalFilename();
            if (Objects.isNull(filename)) {
                throw new StorageException("Failed to store file with empty name");
            }
            String originalFilename = filename;

            filename = UUID.randomUUID() + "-" + filename;

            ObjectWriteResponse putObjectResponse = minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .build()
            );

            File storedFile = File.builder()
                    .originalName(originalFilename)
                    .storedName(filename)
                    .build();

            return documentRepository.save(storedFile);
        } catch (IOException e) {
            throw new StorageException("Failed to store file", e);
        }
    }

    @SneakyThrows
    @Override
    public Resource loadAsResource(String filename) {
        try {
            var bytes = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .build()
            ).readAllBytes();

            return new ByteArrayResource(bytes);
        } catch (ErrorResponseException e) {
            if (e.errorResponse().code().equals("NoSuchKey")) {
                throw new NotFoundException("Failed to read file: " + filename);
            } else {
                throw e;
            }
        }
    }

    @Override
    @Transactional
    @Cacheable(value = "file_original_name", key = "filename")
    public String getOriginalName(String filename) {
        return documentRepository.findByStoredName(filename)
                .map(File::getOriginalName)
                .orElseThrow(() -> new NotFoundException("Failed to read file: " + filename));
    }

    @Override
    @CacheEvict(value = "file_original_name", allEntries = true)
    public void deleteAll() {
        var objects = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .build()
        );


        List<DeleteObject> deleteObjects = Streams.stream(objects)
                .map(itemResult -> {
                    try {
                        return itemResult.get().objectName();
                    } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(DeleteObject::new)
                .toList();

        minioClient.removeObjects(
                RemoveObjectsArgs.builder()
                        .bucket(bucketName)
                        .objects(deleteObjects)
                        .build()
        );
    }
}