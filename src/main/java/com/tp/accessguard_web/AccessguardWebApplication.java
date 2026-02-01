package com.tp.accessguard_web;

import com.tp.accessguard_web.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.SQLOutput;

@SpringBootApplication
public class AccessguardWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessguardWebApplication.class, args);
	}

    @Bean
    CommandLineRunner testPerson(PersonRepository repo){
        return args -> {
            System.out.println("=== PERSONAS EN BD ===");
            repo.findAll().forEach(p ->
                    System.out.println(p.getId() + " - " + p.getFullName() + " - " + p.getStatus()))
        ;};
    }
}
