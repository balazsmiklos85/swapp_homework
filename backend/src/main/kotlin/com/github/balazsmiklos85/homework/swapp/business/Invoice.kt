package com.github.balazsmiklos85.homework.swapp.business

import com.github.balazsmiklos85.homework.swapp.data.InvoiceRow
import java.math.BigDecimal

class Invoice(val prices: List<InvoiceRow>, val total: BigDecimal)
