package com.github.balazsmiklos85.homework.swapp.api

import com.github.balazsmiklos85.homework.swapp.business.Invoice
import com.github.balazsmiklos85.homework.swapp.data.Row
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import java.math.BigDecimal
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.slf4j.LoggerFactory

@CrossOrigin(origins = ["*"])
@RestController
@Api(value = "Invoice generation",
     description = "Handles invoice generation. Provides data that can be used in invoices, and generates the invoice files.")
class RestApi {
    private val logger = LoggerFactory.getLogger(RestApi::class.java)

    @ApiOperation(value = "Gets items for the invoice generation.",
                  notes = "Items have a name and a price (amount) and they can be used as an input for the invoice generation.")
    @GetMapping("/data")
    fun getData(): List<Row> { // TODO: these values don't need "selected" values yet
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

    @ApiOperation(value = "Generates invoices.",
                  notes = "Invoice generation produces a PDF file. The file can be accessed by the ID provided in the result at /invoices/{id}")
    @PostMapping("/invoice")
    fun createInvoice(@RequestBody invoiceData: List<Row>) : String { // TODO do not return a String, return it in a JSON Object instead
        val invoice = Invoice(getData(), invoiceData)
        invoice.generate()
        return invoice.id
    }
}
