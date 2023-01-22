package com.github.balazsmiklos85.homework.swapp.business

import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.element.Paragraph
import org.springframework.stereotype.Service

@Service
class PdfFactory(val storageService: StorageService){
    fun save(invoice: Invoice): String {
        val id = storageService.getId()
        val doc = createDocument(id)
        val table = createTable()
        addInfoToTable(invoice, table)
        doc.add(table)
        doc.add(Paragraph("Total Amount: ${invoice.total}"))
        doc.close()
        return id
    }

    private fun addInfoToTable(invoice: Invoice, table: Table) {
        invoice.prices.forEach { row ->
            table.addCell(createCell("${row.name}:"))
            table.addCell(createCell("${row.amount}"))
        }
    }

    private fun createCell(text: String): Cell {
        val result = Cell()
        result.add(Paragraph(text))
        return result
    }

    private fun createDocument(id: String): Document {
        val file = storageService.getFile(id)
        val writer = PdfWriter(file)
        val pdf = PdfDocument(writer)
        return Document(pdf)
    }

    private fun createTable(): Table {
        val table = Table(2)
        table.addCell(createCell("Name"))
        table.addCell(createCell("Amount"))
        return table
    }
}