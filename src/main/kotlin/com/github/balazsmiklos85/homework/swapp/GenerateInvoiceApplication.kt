package com.github.balazsmiklos85.homework.swapp

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GenerateInvoiceApplication

fun main(args: Array<String>) {
    runApplication<GenerateInvoiceApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
