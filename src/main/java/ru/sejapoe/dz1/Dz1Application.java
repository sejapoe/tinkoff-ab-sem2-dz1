package ru.sejapoe.dz1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.sejapoe.dz1.config.JwtProperties;
import ru.sejapoe.dz1.config.StorageProperties;

@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class, JwtProperties.class})
@EnableMongoRepositories
public class Dz1Application {

    public static void main(String[] args) {
        SpringApplication.run(Dz1Application.class, args);
    }

}
