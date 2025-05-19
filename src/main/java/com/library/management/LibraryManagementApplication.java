package com.library.management;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main entry point for the Library Management System Spring Boot application.
 * <p>
 * This class is responsible for bootstrapping the Spring Boot context and initializing
 * application-wide beans such as {@link ModelMapper}.
 * </p>
 *
 * <p>
 * The application is designed to manage books, borrowers, and book lending/return processes
 * through a RESTful API using Spring Boot and JPA.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @since 2025-05-19
 */
@SpringBootApplication
public class LibraryManagementApplication {

    /**
     * Main method that launches the Spring Boot application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementApplication.class, args);
    }

    /**
     * Configures and returns a {@link ModelMapper} bean with strict matching strategy.
     * <p>
     * This bean is used to map between DTOs and entities throughout the application.
     * </p>
     *
     * @return a configured {@link ModelMapper} instance
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}
