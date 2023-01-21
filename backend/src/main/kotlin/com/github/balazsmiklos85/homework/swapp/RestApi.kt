package com.github.balazsmiklos85.homework.swapp

import java.math.BigDecimal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RestApi {
    @GetMapping("/data")
    fun getData(): List<Row> {
        return listOf(
                Row("Hamburger", BigDecimal("5.49")),
                Row("Big Hamburger", BigDecimal("6.93")),
                Row("Small Hamburger", BigDecimal("2.87")),
                Row("Chicken Burger", BigDecimal("6.54")),
                Row("Fish Burger", BigDecimal("5.23")),
                Row("Small Fries", BigDecimal("2.34")),
                Row("Medium Fries", BigDecimal("2.87")),
                Row("Large Fries", BigDecimal("3.13"))
        )
    }

    @PostMapping("/invoice")
    fun createInvoice(@RequestBody invoiceData: List<Row>) : String {
        val invoice = Invoice(invoiceData)
        invoice.generate()
        return invoice.fileName
    }

    // FIXME remove before releasing
    @GetMapping("/invoice")
    fun testInvoiceCreation() : String {
        val invoiceData = listOf(
              Row("Hamburger", BigDecimal("5.49"), true),
              Row("Big Hamburger", BigDecimal("6.93"), true),
              Row("Small Hamburger", BigDecimal("2.87"), true),
              Row("Chicken Burger", BigDecimal("6.54"), true),
              Row("Fish Burger", BigDecimal("5.23"), true),
              Row("Small Fries", BigDecimal("2.34"), true),
              Row("Medium Fries", BigDecimal("2.87"), true),
              Row("Large Fries", BigDecimal("3.13"), true))
        val invoice = Invoice(invoiceData)
        invoice.generate()
        return invoice.fileName
    }
}
