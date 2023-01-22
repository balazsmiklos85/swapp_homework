package com.github.balazsmiklos85.homework.swapp.business

import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Service
import java.io.File
import java.util.UUID

private const val STORAGE_DIRECTORY = "/tmp"

@Service
class StorageService{
    fun getFile(id: String) = File(getFilePath(id))

    fun getFileName(id: String) = "invoice-$id.pdf"

    fun getFileSystemResource(id: String) = FileSystemResource(getFilePath(id))

    fun getId() = UUID.randomUUID().toString()

    private fun getFilePath(id: String) = "$STORAGE_DIRECTORY/${getFileName(id)}"
}
