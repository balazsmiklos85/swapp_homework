package com.github.balazsmiklos85.homework.swapp

import java.math.BigDecimal

data class Row(val name: String, val amount: BigDecimal, val selected: Boolean) {
	constructor(name: String, amount: BigDecimal) : this(name, amount, false)
}