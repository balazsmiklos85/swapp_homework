package com.github.balazsmiklos85.homework.swapp.api

import com.github.balazsmiklos85.homework.swapp.business.DataService
import com.github.balazsmiklos85.homework.swapp.data.InvoiceRow
import com.github.balazsmiklos85.homework.swapp.data.InvoiceSelection
import com.github.balazsmiklos85.homework.swapp.business.InvoiceFactory
import com.github.balazsmiklos85.homework.swapp.business.PdfFactory
import com.github.balazsmiklos85.homework.swapp.data.PdfResource
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
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
class InvoiceController(val dataService: DataService, val invoiceFactory: InvoiceFactory, val pdfFactory: PdfFactory) {
    private val logger = LoggerFactory.getLogger(InvoiceController::class.java)

    @ApiOperation(value = "Gets items for the invoice generation.",
                  notes = "Items have a name and a price (amount) and they can be used as an input for the invoice generation.")
    @GetMapping("/data")
    fun getData(): List<InvoiceRow> {
        logger.info("Data fetched.")
        return dataService.getAllRows()
    }

    @ApiOperation(value = "Generates invoices.",
                  notes = "Invoice generation produces a PDF file. The file can be accessed by the ID provided in the result at /invoices/{id}")
    @PostMapping("/invoice")
    fun createInvoice(@RequestBody invoiceSelection: List<InvoiceSelection>) : PdfResource {
        val invoice = invoiceFactory.create(invoiceSelection)
        val id = pdfFactory.save(invoice)
        logger.info("{} invoice created.", id)
        return PdfResource(id)
    }
}
