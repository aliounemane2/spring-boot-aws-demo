package com.sivalabs.geeks.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("app")
class ApplicationProperties {
    var mockEnv = false
    var bucketName: String = "geeks"
    var storageBackend: String = "s3"
    var storagePath: String = ""
}
