package com.github.balazsmiklos85.homework.swapp.error

import java.lang.RuntimeException

class ItemNotFoundException(message: String): RuntimeException(message)
