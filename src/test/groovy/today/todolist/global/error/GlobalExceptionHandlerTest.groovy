package today.todolist.global.error


import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.FieldError
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import spock.lang.Specification
import today.todolist.global.error.dto.BasicErrorResponse
import today.todolist.global.error.dto.InvoluteErrorResponse
import today.todolist.global.error.dto.SimpleErrorResponse
import today.todolist.global.error.exception.BasicException

import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException

import static today.todolist.global.error.ErrorCode.*

class GlobalExceptionHandlerTest extends Specification {

    GlobalExceptionHandler handler = new GlobalExceptionHandler()

    def "handle Exception" () {
        given:
        ErrorCode errorCode = INTERNAL_SERVER_ERROR
        Exception exception = new Exception()

        when:
        ResponseEntity<BasicErrorResponse> response = handler.handleException(exception)
        // convert
        BasicErrorResponse body = response.body

        then:
        response.statusCode.value() == errorCode.status
        body.status == errorCode.status
        body.code == errorCode.code
        body.message == errorCode.message
    }

    def "handle AccessDeniedException" () {
        given:
        ErrorCode errorCode = UNAUTHORIZED_REQUEST
        AccessDeniedException exception = new AccessDeniedException("TEST")

        when:
        ResponseEntity<BasicErrorResponse> response = handler.handleAccessDeniedException(exception)
        // convert
        BasicErrorResponse body = response.body

        then:
        response.statusCode.value() == errorCode.status
        body.status == errorCode.status
        body.code == errorCode.code
        body.message == errorCode.message
    }

    def "handle MethodArgumentNotValidException" () {
        given:
        ErrorCode errorCode = MISSING_REQUEST
        MethodArgumentNotValidException exception = Stub()
        exception.fieldErrors >> [new FieldError("TEST-Object", "TEST-Field", "TEST-Message")]

        when:
        ResponseEntity<BasicErrorResponse> response = handler.handleMethodArgumentNotValidException(exception)
        // convert
        InvoluteErrorResponse body = response.body

        then:
        response.statusCode.value() == errorCode.status
        body.status == errorCode.status
        body.code == errorCode.code
        body.message == errorCode.message
        body.reason["TEST-Field"] == "TEST-Message"
    }

    def "handle ConstraintViolationException" () {
        given:
        ErrorCode errorCode = MISSING_REQUEST
        ConstraintViolationException exception = new ConstraintViolationException("TEST", new HashSet<ConstraintViolation<?>>())

        when:
        ResponseEntity<BasicErrorResponse> response = handler.handleConstraintViolationException(exception)
        // convert
        SimpleErrorResponse body = response.body

        then:
        response.statusCode.value() == errorCode.status
        body.status == errorCode.status
        body.code == errorCode.code
        body.message == errorCode.message
        body.reason == exception.message
    }

    def "handle MissingServletRequestParameterException" () {
        given:
        ErrorCode errorCode = MISSING_REQUEST
        MissingServletRequestParameterException exception = new MissingServletRequestParameterException("TEST", "TEST")

        when:
        ResponseEntity<BasicErrorResponse> response = handler.handleMissingServletRequestParameterException(exception)
        // convert
        SimpleErrorResponse body = response.body

        then:
        response.statusCode.value() == errorCode.status
        body.status == errorCode.status
        body.code == errorCode.code
        body.message == errorCode.message
        body.reason == exception.message
    }

    def "handle HttpMediaTypeNotSupportedException" () {
        given:
        ErrorCode errorCode = NOT_IN_JSON_FORMAT
        HttpMediaTypeNotSupportedException exception = new HttpMediaTypeNotSupportedException("TEST")

        when:
        ResponseEntity<BasicErrorResponse> response = handler.handleHttpMediaTypeNotSupportedException(exception)
        // convert
        SimpleErrorResponse body = response.body

        then:
        response.statusCode.value() == errorCode.status
        body.status == errorCode.status
        body.code == errorCode.code
        body.message == errorCode.message
        body.reason == "The content type must be application/json. But request content type is null"
    }

    def "handle HttpMessageNotReadableException" () {
        given:
        ErrorCode errorCode = WRONG_JSON_FORMAT
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException("TEST")

        when:
        ResponseEntity<BasicErrorResponse> response = handler.handleHttpMessageNotReadableException(exception)
        // convert
        SimpleErrorResponse body = response.body

        then:
        response.statusCode.value() == errorCode.status
        body.status == errorCode.status
        body.code == errorCode.code
        body.message == errorCode.message
        body.reason == exception.message
    }

    def "handle HttpRequestMethodNotSupportedException" () {
        given:
        ErrorCode errorCode = WRONG_HTTP_METHOD
        HttpRequestMethodNotSupportedException exception = new HttpRequestMethodNotSupportedException("GET", ["POST"])

        when:
        ResponseEntity<BasicErrorResponse> response = handler.handleHttpRequestMethodNotSupportedException(exception)
        // convert
        SimpleErrorResponse body = response.body

        then:
        response.statusCode.value() == errorCode.status
        body.status == errorCode.status
        body.code == errorCode.code
        body.message == errorCode.message
        body.reason == "Request method GET not supported"
    }

    def "handle GlobalException" () {
        given:
        ErrorCode errorCode = INTERNAL_SERVER_ERROR
        BasicException exception = new BasicException(errorCode)

        when:
        ResponseEntity<BasicErrorResponse> response = handler.handleGlobalException(exception)
        // convert
        BasicErrorResponse body = response.body

        then:
        response.statusCode.value() == errorCode.status
        body.status == errorCode.status
        body.code == errorCode.code
        body.message == errorCode.message
    }

}