package com.example.demo.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

private const val REPOSITORY_NOT_FOUND = "Repository not found"
private const val INCORRECT_DATE_FORMAT = "Incorrect date format"

@RestControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(RepositoryNotFoundException::class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun handleRepositoryNotFoundException(repositoryNotFoundException: RepositoryNotFoundException): CustomErrorResponse{
        return CustomErrorResponse(HttpStatus.NOT_FOUND.value(), REPOSITORY_NOT_FOUND)
    }

    @ExceptionHandler(IncorrectDateFormatException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun handleWrongDateFormatException(incorrectDateFormatException: IncorrectDateFormatException): CustomErrorResponse{
        return CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), INCORRECT_DATE_FORMAT)
    }
}