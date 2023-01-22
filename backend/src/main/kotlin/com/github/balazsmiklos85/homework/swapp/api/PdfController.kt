package com.github.balazsmiklos85.homework.swapp.api

import com.github.balazsmiklos85.homework.swapp.business.StorageService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable

@Controller
@Api(value = "PDF file access.",
     description = "Provides downloads for the generated invoices.")
class PdfController(val storageService: StorageService) {
    private val logger = LoggerFactory.getLogger(PdfController::class.java)

    @GetMapping("/")
    fun redirect(model: Model): String { //TODO it should redirect to the nodejs host
        model["title"] = "Generate invoice"
        return "generate_invoice"
    }

    @ApiOperation(value = "Generates invoices.",
                  notes = "Invoice generation produces a PDF file. The file can be accessed by the ID provided in the result.")
    @GetMapping("/invoices/{id}")
    fun download(@PathVariable id: String): ResponseEntity<FileSystemResource> {
        return if (storageService.getFile(id).exists()) {
            logger.info("$id invoice fetched.")
            ResponseEntity.ok()
                          .contentType(MediaType.APPLICATION_PDF)
                          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${storageService.getFileName(id)}")
                          .body(storageService.getFileSystemResource(id))
        } else {
            logger.warn("Non-existent invoice '$id' fetched.")
            ResponseEntity.notFound().build()
        }
    }
}
