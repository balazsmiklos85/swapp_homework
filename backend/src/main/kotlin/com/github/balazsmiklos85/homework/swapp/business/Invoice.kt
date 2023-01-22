package com.github.balazsmiklos85.homework.swapp.business

import com.github.balazsmiklos85.homework.swapp.data.Row
import com.github.balazsmiklos85.homework.swapp.error.ItemNotFoundException
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.element.Paragraph
import java.io.File
import java.math.BigDecimal
import java.util.UUID

const val STORAGE_DIRECTORY = "/tmp"

class Invoice(dataWithPrices: List<Row>, filteredInvoiceData: List<Row>) {
    val id: String = UUID.randomUUID().toString()
    private val invoiceData: List<Row>
    private val total: BigDecimal

    init {
        this.invoiceData = filteredInvoiceData.stream()
                                              .map { Row(it.name, lookUpAmount(dataWithPrices, it.name), true) } // TODO selected is not needed here either
                                              .toList()
        this.total = invoiceData.stream()
                                .map { row -> row.amount }
                                .reduce(BigDecimal.ZERO) { result, amount -> result + amount }
    }

    fun generate() { // TODO make this more testable
        val file = File("$STORAGE_DIRECTORY/invoice-$id.pdf")
        val writer = PdfWriter(file)
        val pdf = PdfDocument(writer)
        val doc = Document(pdf)
        val table = Table(2)
        table.addCell(createCell("Name"))
        table.addCell(createCell("Amount"))
        invoiceData.forEach { row ->
            table.addCell(createCell("${row.name}:"))
            table.addCell(createCell("${row.amount}"))
        }
        doc.add(table)
        doc.add(Paragraph("Total Amount: $total"))
        doc.close()
    }

    private fun createCell(text: String): Cell {
        val result = Cell()
        result.add(Paragraph(text))
        return result
    }

    private fun lookUpAmount(dataWithPrices: List<Row>, name: String) : BigDecimal {
        return dataWithPrices.stream()
                             .filter { it.name == name }
                             .findFirst()
                             .map { it.amount }
                             .orElseThrow { ItemNotFoundException("Unknown item cannot be added to the invoice") }
    }
}
