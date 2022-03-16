package com.sivalabs.geeks

import com.sivalabs.geeks.config.ApplicationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties::class)
class SpringBootGeeksApplication

fun main(args: Array<String>) {
    runApplication<SpringBootGeeksApplication>(*args)
}
