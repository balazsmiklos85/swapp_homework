package com.github.balazsmiklos85.homework.swapp

import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.element.Paragraph
import java.io.File
import java.math.BigDecimal
import java.util.UUID

private const val STORAGE_DIRECTORY = "/tmp"

class Invoice {
    val fileName: String="invoice-${UUID.randomUUID()}.pdf"
    private val invoiceData: List<Row>
    private val total: BigDecimal

    constructor(filteredInvoiceData: List<Row>, total: BigDecimal) {
        this.invoiceData = filteredInvoiceData
        this.total = total
    }

    constructor(invoiceData: List<Row>) : this(filterInvoiceData(invoiceData), calculateTotal(invoiceData))

    fun generate() {
        val file = File("$STORAGE_DIRECTORY/$fileName")
        val writer = PdfWriter(file)
        val pdf = PdfDocument(writer)
        val doc = Document(pdf)
        val table = Table(2)
        invoiceData.forEach { row ->
            table.addCell(createCell("${row.name}:"))
            table.addCell(createCell("${row.amount}"))
        }
        doc.add(table);
        doc.add(Paragraph("Total Amount: $total"))
        doc.close()
    }

    private fun createCell(text: String): Cell {
        val result = Cell()
        result.add(Paragraph(text))
        return result
    }

    companion object {
        private fun calculateTotal(invoiceData: List<Row>): BigDecimal {
            return invoiceData.stream()
                .map { row -> row.amount }
                .reduce(BigDecimal.ZERO) { result, amount -> result + amount }
        }

        private fun filterInvoiceData(invoiceData: List<Row>): List<Row> {
            return invoiceData.stream()
                .filter { row -> row != null }
                .filter { row -> row.selected }
                .toList()
        }
    }
}
