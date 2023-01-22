package com.github.balazsmiklos85.homework.swapp.business

import com.github.balazsmiklos85.homework.swapp.data.Invoice
import com.github.balazsmiklos85.homework.swapp.data.InvoiceRow
import com.github.balazsmiklos85.homework.swapp.data.InvoiceSelection
import com.github.balazsmiklos85.homework.swapp.error.ItemNotFoundException
import java.math.BigDecimal
import org.springframework.stereotype.Service

@Service
class InvoiceFactory(val dataService: DataService) {
    fun create(invoiceSelection: List<InvoiceSelection>): Invoice {
        val prices = invoiceSelection
            .stream()
            .map { InvoiceRow(it.name, lookUpAmount(dataService.getAllRows(), it.name)) }
            .toList()
        val total = prices
            .stream()
            .map { row -> row.amount }
            .reduce(BigDecimal.ZERO) { result, amount -> result + amount }
        return Invoice(prices, total)
    }

    private fun lookUpAmount(dataWithPrices: List<InvoiceRow>, name: String): BigDecimal {
        return dataWithPrices
            .stream()
            .filter { it.name == name }
            .findFirst()
            .map { it.amount }
            .orElseThrow {
                ItemNotFoundException("Unknown item cannot be added to the invoice")
            }
    }
}
