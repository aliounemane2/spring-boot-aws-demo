package com.sivalabs.geeks.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.HiddenHttpMethodFilter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {
    @Bean
    fun hiddenHttpMethodFilter(): HiddenHttpMethodFilter? {
        return HiddenHttpMethodFilter()
    }
}
