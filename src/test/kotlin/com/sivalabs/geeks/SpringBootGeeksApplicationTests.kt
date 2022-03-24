package com.sivalabs.geeks

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.containers.localstack.LocalStackContainer.Service.S3
import org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS
import org.testcontainers.utility.DockerImageName

@SpringBootTest
@ActiveProfiles("test")
class SpringBootGeeksApplicationTests {

    @TestConfiguration
    class TestConfig {
        private val logger = LoggerFactory.getLogger(javaClass)

        companion object {
            private val credentialsProvider: AWSCredentialsProvider = getCredentialsProvider()
            private val localStackContainer: LocalStackContainer = LocalStackContainer(
                DockerImageName.parse("localstack/localstack:0.11.2"))
                .withServices(S3, SQS)
                .withExposedPorts(4566)
            init {
                localStackContainer.start()
            }

            private fun getCredentialsProvider(): AWSCredentialsProvider {
                return AWSStaticCredentialsProvider(BasicAWSCredentials("test", "test"))
            }
        }

        @Bean
        @Primary
        fun amazonS3LocalStack(): AmazonS3 {
           logger.debug("Creating test localstack AmazonS3 client")
            return AmazonS3ClientBuilder.standard()
                .enablePathStyleAccess()
                .withEndpointConfiguration(localStackContainer.getEndpointConfiguration(S3))
                .withCredentials(credentialsProvider)
                .build()
        }
    }

    @Test
    fun contextLoads() {
    }

}
