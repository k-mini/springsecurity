package com.kmini.springsecurity.config;

import com.kmini.springsecurity.repository.AccessIpRepository;
import com.kmini.springsecurity.repository.ResourcesRepository;
import com.kmini.springsecurity.service.SecurityResourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public SecurityResourceService resourceService(ResourcesRepository resourcesRepository,
                                                   AccessIpRepository accessIpRepository) {
        return new SecurityResourceService(resourcesRepository, accessIpRepository);
    }
}
