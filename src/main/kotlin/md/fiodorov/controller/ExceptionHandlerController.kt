package md.fiodorov.controller

import md.fiodorov.utils.ApiError
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.stream.Collectors
import javax.validation.ConstraintViolationException


/**
 * @author rfiodorov
 * on 27/09/17.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
class ExceptionHandlerController {

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleAnyException(exception: ConstraintViolationException): ResponseEntity<Any> {
        val apiError = ApiError(HttpStatus.BAD_REQUEST)
        apiError.message = "Constraint violation exception"

        val constraintViolations = exception.constraintViolations
        val subErrors = constraintViolations.stream().map{it->it.message}.collect(Collectors.toList())
        apiError.subErrors = subErrors
        return buildResponseEntity(apiError)
    }

    private fun buildResponseEntity(apiError: ApiError): ResponseEntity<Any> {
        return ResponseEntity(apiError, apiError.status)
    }

}
