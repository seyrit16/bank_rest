package com.example.bankcards.config.init;

import com.example.bankcards.dto.request.CreateUserRequest;
import com.example.bankcards.entity.invariants.Role;
import com.example.bankcards.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBaseInitializationConfig {
    private final UserService userService;

    public DataBaseInitializationConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public CommandLineRunner initDataBase() {
        return args -> {
            if (!userService.existsByRole(Role.ROLE_ADMIN))
                userService.createUser(new CreateUserRequest("ADMIN","ADMIN", "ADMIN@mail.ru", "ADMIN"), Role.ROLE_ADMIN);
        };
    }
}
