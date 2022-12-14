package com.sivalabs.geeks.config

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import io.awspring.cloud.autoconfigure.context.properties.AwsRegionProperties
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment

@Configuration
@Profile("!test")
class AwsConfig(
    private val properties: ApplicationProperties,
    private val environment: Environment
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun amazonS3(regionProperties: AwsRegionProperties): AmazonS3 {
        logger.debug("Creating localstack amazonS3 client")
        val builder = AmazonS3ClientBuilder.standard().enablePathStyleAccess()
        this.applyEndpointOverride(builder, "s3")
        val amazonS3 = builder.build()
        amazonS3.createBucket(properties.bucketName)
        return amazonS3
    }

    private fun applyEndpointOverride(builder: AwsClientBuilder<*, *>, service: String) {
        logger.debug("Applying endpoint override for service: {}, is-mock-env: {}", service, properties.mockEnv)
        if (properties.mockEnv) {
            builder.withEndpointConfiguration(getEndpointConfiguration(service))
            builder.withCredentials(getCredentialsProvider())
        }
    }

    private fun getEndpointConfiguration(service: String): EndpointConfiguration {
        val endpoint: String = environment.getProperty("cloud.aws.$service.endpoint")!!
        val region: String = environment.getProperty("cloud.aws.region.static")!!
        logger.debug("service: {}, endpoint: {}, region:{}", service, endpoint, region)
        return EndpointConfiguration(endpoint, region)
    }

    private fun getCredentialsProvider(): AWSCredentialsProvider {
        return AWSStaticCredentialsProvider(BasicAWSCredentials("test", "test"))
    }
}
