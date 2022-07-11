package today.todolist.global.error

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import today.todolist.global.error.ErrorCode.*
import today.todolist.global.error.dto.BasicErrorResponse
import today.todolist.global.error.dto.InvoluteErrorResponse
import today.todolist.global.error.dto.SimpleErrorResponse
import today.todolist.global.error.exception.BasicException
import today.todolist.global.error.exception.GlobalException
import today.todolist.global.error.exception.InvoluteException
import today.todolist.global.error.exception.SimpleException
import javax.validation.ConstraintViolationException

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<BasicErrorResponse> {
        val response = BasicErrorResponse(INTERNAL_SERVER_ERROR)
        return ResponseEntity<BasicErrorResponse>(response, HttpStatus.valueOf(response.status))
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(e: AccessDeniedException): ResponseEntity<BasicErrorResponse> {
        val response = BasicErrorResponse(UNAUTHORIZED_REQUEST)
        return ResponseEntity<BasicErrorResponse>(response, HttpStatus.valueOf(response.status))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<InvoluteErrorResponse> {
        val fieldErrors = e.fieldErrors
        val reasons: MutableMap<String, String> = HashMap(fieldErrors.size)
        e.fieldErrors.forEach {
            fieldError -> reasons[fieldError.field] = fieldError.defaultMessage!!
        }
        val response = InvoluteErrorResponse(MISSING_REQUEST, reasons.toMap())
        return ResponseEntity<InvoluteErrorResponse>(response, HttpStatus.valueOf(response.status))
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException): ResponseEntity<SimpleErrorResponse> {
        val response = SimpleErrorResponse(MISSING_REQUEST, e.message!!)
        return ResponseEntity<SimpleErrorResponse>(response, HttpStatus.valueOf(response.status))
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(e: MissingServletRequestParameterException): ResponseEntity<SimpleErrorResponse> {
        val response = SimpleErrorResponse(MISSING_REQUEST, e.message)
        return ResponseEntity<SimpleErrorResponse>(response, HttpStatus.valueOf(response.status))
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun handleHttpMediaTypeNotSupportedException(e: HttpMediaTypeNotSupportedException): ResponseEntity<SimpleErrorResponse> {
        val response = SimpleErrorResponse(NOT_IN_JSON_FORMAT, e.message!!)
        return ResponseEntity<SimpleErrorResponse>(response, HttpStatus.valueOf(response.status))
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<SimpleErrorResponse> {
        val response = SimpleErrorResponse(WRONG_JSON_FORMAT, e.message!!)
        return ResponseEntity<SimpleErrorResponse>(response, HttpStatus.valueOf(response.status))
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ResponseEntity<SimpleErrorResponse> {
        val reason = "Request method " + e.method + " not supported"
        val response = SimpleErrorResponse(WRONG_HTTP_METHOD, reason)
        return ResponseEntity<SimpleErrorResponse>(response, HttpStatus.valueOf(response.status))
    }

    @ExceptionHandler(*[BasicException::class, InvoluteException::class, SimpleException::class])
    fun <T : GlobalException<R>, R : BasicErrorResponse> handleGlobalException(e: T): ResponseEntity<R> {
        val response: R = e.toErrorResponse()
        return ResponseEntity<R>(response, HttpStatus.valueOf(response.status))
    }
}