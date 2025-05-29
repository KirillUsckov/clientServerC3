package ru.kduskov.lb1maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "ru.kduskov.lb1")
public class Lb1mavenApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lb1mavenApplication.class, args);
    }

}
