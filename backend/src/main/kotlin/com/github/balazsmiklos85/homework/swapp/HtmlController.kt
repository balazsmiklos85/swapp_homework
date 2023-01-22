package com.github.balazsmiklos85.homework.swapp

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.io.File

@Controller
class HtmlController {
    @GetMapping("/")
    fun redirect(model: Model): String { //TODO it should redirect to the nodejs host
        model["title"] = "Generate invoice"
        return "generate_invoice"
    }

    @GetMapping("/invoices/{id}")
    fun download(@PathVariable id: String): ResponseEntity<FileSystemResource> {
        return if (File("$STORAGE_DIRECTORY/invoice-$id.pdf").exists()) { // TODO duplicated logic for path resolution
            ResponseEntity.ok()
                          .contentType(MediaType.APPLICATION_PDF)
                          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice-$id.pdf") // TODO include in path refactor
                          .body(FileSystemResource("$STORAGE_DIRECTORY/invoice-$id.pdf")) // TODO include in path refactor
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
