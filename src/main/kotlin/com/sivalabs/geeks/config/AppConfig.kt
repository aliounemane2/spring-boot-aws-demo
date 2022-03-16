package com.sivalabs.geeks.config

import com.sivalabs.geeks.domain.FileSystemImageService
import com.sivalabs.geeks.domain.ImageService
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    @ConditionalOnMissingBean
    fun imageService(properties: ApplicationProperties) : ImageService {
        return FileSystemImageService(properties);
    }
}
