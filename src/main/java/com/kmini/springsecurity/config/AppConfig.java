package com.kmini.springsecurity.config;

import com.kmini.springsecurity.repository.ResourcesRepository;
import com.kmini.springsecurity.service.SecurityResourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public SecurityResourceService resourceService(ResourcesRepository resourcesRepository) {
        return new SecurityResourceService(resourcesRepository);
    }


}
