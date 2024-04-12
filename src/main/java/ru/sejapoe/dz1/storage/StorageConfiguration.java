package ru.sejapoe.dz1.storage;


import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sejapoe.dz1.config.StorageProperties;

@Configuration
public class StorageConfiguration {
    @Bean
    public MinioClient minioClient(StorageProperties storageProperties) {
        return MinioClient.builder()
                .credentials(storageProperties.getAccessKey(), storageProperties.getSecretKey())
                .region(storageProperties.getRegion())
                .endpoint(storageProperties.getEndpoint())
                .build();
    }
}
