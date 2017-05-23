package ru.levry.dbc.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author levry
 */
@SpringBootApplication
public class DbcApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DbcApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(DbcApplication.class, args);
    }
}
