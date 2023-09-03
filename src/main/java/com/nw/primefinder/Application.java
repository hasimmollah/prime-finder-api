package com.nw.primefinder;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.System.exit;

@SpringBootApplication
@Slf4j
public class Application {
    @SneakyThrows
    public static void main(String[] args) {
        try (InputStream in = args.length > 0 ? new FileInputStream(args[0]) : Application.class.getResourceAsStream("/conf.properties")) {
            Properties properties = new Properties();
            properties.load(in);
            properties.stringPropertyNames().forEach(key -> {
                System.setProperty(key, properties.getProperty(key));
            });
        } catch (Exception e) {
            log.error("Failed to start the application", e);
            exit(1);
        }
        SpringApplication.run(Application.class, args);
    }
}