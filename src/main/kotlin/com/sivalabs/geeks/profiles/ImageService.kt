package com.sivalabs.geeks.profiles;

import java.io.InputStream

interface ImageService {
    fun upload(filename: String, inputStream: InputStream): String

    fun download(filename: String): InputStream?
}
