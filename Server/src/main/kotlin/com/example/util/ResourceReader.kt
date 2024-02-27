package com.example.util

import java.nio.file.Files
import java.nio.file.Path

object ResourceReader {
    fun readResourceFile(filename: String) : String? {
        val uri = this.javaClass.getResource("/$filename")?.toURI()
        return try { Files.readString(uri?.let { Path.of(it) }) }
        catch (e: Exception) { null }
    }
}