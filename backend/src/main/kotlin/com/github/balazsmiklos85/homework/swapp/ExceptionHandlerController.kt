package com.github.balazsmiklos85.homework.swapp

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlerController {
    @ExceptionHandler
    fun handleArticleNotFoundException(e: ItemNotFoundException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            e.message
        )
        return ResponseEntity(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY)
    }
}
