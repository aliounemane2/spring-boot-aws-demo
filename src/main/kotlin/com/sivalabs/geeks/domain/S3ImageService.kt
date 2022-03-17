package com.sivalabs.geeks.domain

import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.sivalabs.geeks.config.ApplicationProperties
import org.apache.commons.io.IOUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.lang.RuntimeException

@Component
class S3ImageService(private val properties: ApplicationProperties,
                     private val amazonS3: AmazonS3
                     ) : ImageService {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun upload(filename: String, inputStream: InputStream): String {
        logger.debug("Uploading image to S3")
        try {
            val metadata = ObjectMetadata()
            val bytes = IOUtils.toByteArray(inputStream)
            metadata.contentLength = bytes.size.toLong()
            val byteArrayInputStream = ByteArrayInputStream(bytes)
            val key: String = filename
            val putObjectRequest = PutObjectRequest(properties.bucketName, key, byteArrayInputStream, metadata)
            amazonS3.putObject(putObjectRequest)
        } catch (serviceException: AmazonServiceException) {
            logger.error("AmazonServiceException: " + serviceException.message)
            throw serviceException
        } catch (clientException: AmazonClientException) {
            logger.error("AmazonClientException Message: " + clientException.message)
            throw clientException
        } catch (e: IOException) {
            logger.error("IOException: " + e.message)
            throw RuntimeException(e)
        }
        return filename
    }

    override fun download(filename: String): InputStream? {
        try {
            val s3object = amazonS3.getObject(
                GetObjectRequest(properties.bucketName, filename)
            )
            return s3object.objectContent
        } catch (ioException: IOException) {
            logger.error("IOException: " + ioException.message)
        } catch (serviceException: AmazonServiceException) {
            logger.error("AmazonServiceException Message:    " + serviceException.message)
            throw serviceException
        } catch (clientException: AmazonClientException) {
            logger.error("AmazonClientException Message: " + clientException.message)
            throw clientException
        }
        return null
    }
}

