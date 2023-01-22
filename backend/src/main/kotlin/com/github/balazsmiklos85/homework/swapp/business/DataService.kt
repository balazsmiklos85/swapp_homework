package com.github.balazsmiklos85.homework.swapp.business

import com.github.balazsmiklos85.homework.swapp.data.InvoiceRow
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class DataService{
    fun getAllRows(): List<InvoiceRow> {
        return listOf(InvoiceRow("Hamburger", BigDecimal("5.49")),
                      InvoiceRow("Big Hamburger", BigDecimal("6.93")),
                      InvoiceRow("Small Hamburger", BigDecimal("2.87")),
                      InvoiceRow("Chicken Burger", BigDecimal("6.54")),
                      InvoiceRow("Fish Burger", BigDecimal("5.23")),
                      InvoiceRow("Small Fries", BigDecimal("2.34")),
                      InvoiceRow("Medium Fries", BigDecimal("2.87")),
                      InvoiceRow("Large Fries", BigDecimal("3.13"))
        )
    }
}