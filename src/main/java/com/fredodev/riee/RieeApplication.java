package com.fredodev.riee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class RieeApplication {
    private static final String DEFAULT_TIME_ZONE = "America/La_Paz";

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
        SpringApplication.run(RieeApplication.class, args);
    }

}
