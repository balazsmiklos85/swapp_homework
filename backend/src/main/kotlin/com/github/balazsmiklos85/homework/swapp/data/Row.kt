package com.github.balazsmiklos85.homework.swapp.data

import java.math.BigDecimal

data class Row(val name: String, val amount: BigDecimal, val selected: Boolean) { //TODO find a better name
	constructor(name: String, amount: BigDecimal) : this(name, amount, false)
}