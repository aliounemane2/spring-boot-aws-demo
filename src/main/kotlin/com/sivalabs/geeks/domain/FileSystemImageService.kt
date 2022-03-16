package com.sivalabs.geeks.domain

import com.sivalabs.geeks.config.ApplicationProperties
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.*

class FileSystemImageService(private val properties: ApplicationProperties) : ImageService {

    override fun upload(filename: String, inputStream: InputStream): String {
        FileUtils.forceMkdir(File(properties.storagePath))
        val file: File = File(properties.storagePath, filename)
        IOUtils.copy(inputStream, FileOutputStream(file))
        return filename
    }

    override fun download(filename: String): InputStream? {
        val photo: File = File(properties.storagePath, filename)
        return FileInputStream(photo)
    }
}

