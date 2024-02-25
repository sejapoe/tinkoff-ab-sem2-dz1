package ru.sejapoe.dz1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.sejapoe.dz1.config.StorageProperties;

@EnableCaching
@SpringBootApplication
public class Dz1Application {

    public static void main(String[] args) {
        SpringApplication.run(Dz1Application.class, args);
    }

}
