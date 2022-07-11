package today.todolist.global.error

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.FieldError
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import today.todolist.global.error.ErrorCode.*
import today.todolist.global.error.exception.BasicException
import today.todolist.global.error.exception.InvoluteException
import today.todolist.global.error.exception.SimpleException
import javax.validation.ConstraintViolationException

class GlobalExceptionHandlerTest: BehaviorSpec({
    val handler = GlobalExceptionHandler()

    Given("Create Exception") {
        val exception = Exception()

        When("Handle Exception") {
            val http = handler.handleException(exception)

            Then("Check BasicErrorResponse") {
                val response = http.body!!
                val errorCode = INTERNAL_SERVER_ERROR

                http.statusCode.value().shouldBe(errorCode.status)
                response.status.shouldBe(errorCode.status)
                response.code.shouldBe(errorCode.code)
                response.message.shouldBe(errorCode.message)
            }
        }
    }

    Given("Create AccessDeniedException") {
        val exception = AccessDeniedException("msg")

        When("Handle AccessDeniedException") {
            val http = handler.handleAccessDeniedException(exception)

            Then("Check BasicErrorResponse") {
                val response = http.body!!
                val errorCode = UNAUTHORIZED_REQUEST

                http.statusCode.value().shouldBe(errorCode.status)
                response.status.shouldBe(errorCode.status)
                response.code.shouldBe(errorCode.code)
                response.message.shouldBe(errorCode.message)
            }
        }
    }

    Given("Create MethodArgumentNotValidException") {
        val exception = mockk<MethodArgumentNotValidException>()
        every { exception.fieldErrors } returns mutableListOf(FieldError("objectName", "field", "defaultMessage"))

        When("Handle MethodArgumentNotValidException") {
            val http = handler.handleMethodArgumentNotValidException(exception)

            Then("Check InvoluteErrorResponse") {
                val response = http.body!!
                val errorCode = MISSING_REQUEST

                http.statusCode.value().shouldBe(errorCode.status)
                response.status.shouldBe(errorCode.status)
                response.code.shouldBe(errorCode.code)
                response.message.shouldBe(errorCode.message)
                response.reason["field"].shouldBe("defaultMessage")
            }
        }
    }

    Given("Create ConstraintViolationException") {
        val exception = ConstraintViolationException("message", hashSetOf())

        When("Handle ConstraintViolationException") {
            val http = handler.handleConstraintViolationException(exception)

            Then("Check SimpleErrorResponse") {
                val response = http.body!!
                val errorCode = MISSING_REQUEST

                http.statusCode.value().shouldBe(errorCode.status)
                response.status.shouldBe(errorCode.status)
                response.code.shouldBe(errorCode.code)
                response.message.shouldBe(errorCode.message)
                response.reason.shouldBe(exception.message)
            }
        }
    }

    Given("Create MissingServletRequestParameterException") {
        val exception = MissingServletRequestParameterException("parameterName", "parameterType")

        When("Handle MissingServletRequestParameterException") {
            val http = handler.handleMissingServletRequestParameterException(exception)

            Then("Check SimpleErrorResponse") {
                val response = http.body!!
                val errorCode = MISSING_REQUEST

                http.statusCode.value().shouldBe(errorCode.status)
                response.status.shouldBe(errorCode.status)
                response.code.shouldBe(errorCode.code)
                response.message.shouldBe(errorCode.message)
                response.reason.shouldBe(exception.message)
            }
        }
    }

    Given("Create HttpMediaTypeNotSupportedException") {
        val exception = HttpMediaTypeNotSupportedException("message")

        When("Handle HttpMediaTypeNotSupportedException") {
            val http = handler.handleHttpMediaTypeNotSupportedException(exception)

            Then("Check SimpleErrorResponse") {
                val response = http.body!!
                val errorCode = NOT_IN_JSON_FORMAT

                http.statusCode.value().shouldBe(errorCode.status)
                response.status.shouldBe(errorCode.status)
                response.code.shouldBe(errorCode.code)
                response.message.shouldBe(errorCode.message)
                response.reason.shouldBe(exception.message)
            }
        }
    }

    Given("Create HttpMessageNotReadableException") {
        val exception = HttpMessageNotReadableException("msg")

        When("Handle HttpMessageNotReadableException") {
            val http = handler.handleHttpMessageNotReadableException(exception)

            Then("Check SimpleErrorResponse") {
                val response = http.body!!
                val errorCode = WRONG_JSON_FORMAT

                http.statusCode.value().shouldBe(errorCode.status)
                response.status.shouldBe(errorCode.status)
                response.code.shouldBe(errorCode.code)
                response.message.shouldBe(errorCode.message)
                response.reason.shouldBe(exception.message)
            }
        }
    }

    Given("Create HttpRequestMethodNotSupportedException") {
        val exception = HttpRequestMethodNotSupportedException("method")

        When("Handle HttpRequestMethodNotSupportedException") {
            val http = handler.handleHttpRequestMethodNotSupportedException(exception)

            Then("Check SimpleErrorResponse") {
                val response = http.body!!
                val errorCode = WRONG_HTTP_METHOD

                http.statusCode.value().shouldBe(errorCode.status)
                response.status.shouldBe(errorCode.status)
                response.code.shouldBe(errorCode.code)
                response.message.shouldBe(errorCode.message)
                response.reason.shouldBe("Request method " + exception.method + " not supported")
            }
        }
    }

    Given("Evince GlobalException Info") {
        val errorCode = INTERNAL_SERVER_ERROR

        When("Handle BasicException") {
            val http = handler.handleGlobalException(BasicException(errorCode))

            Then("Check BasicErrorResponse") {
                val response = http.body!!

                http.statusCode.value().shouldBe(errorCode.status)
                response.status.shouldBe(errorCode.status)
                response.code.shouldBe(errorCode.code)
                response.message.shouldBe(errorCode.message)
            }
        }

        When("Handle SimpleException") {
            val http = handler.handleGlobalException(SimpleException(errorCode, "reason"))

            Then("Check SimpleErrorResponse") {
                val response = http.body!!

                http.statusCode.value().shouldBe(errorCode.status)
                response.status.shouldBe(errorCode.status)
                response.code.shouldBe(errorCode.code)
                response.message.shouldBe(errorCode.message)
                response.reason.shouldBe("reason")
            }
        }

        When("Handle InvoluteException") {
            val http = handler.handleGlobalException(InvoluteException(errorCode, mapOf()))

            Then("Check InvoluteErrorResponse") {
                val response = http.body!!

                http.statusCode.value().shouldBe(errorCode.status)
                response.status.shouldBe(errorCode.status)
                response.code.shouldBe(errorCode.code)
                response.message.shouldBe(errorCode.message)
                response.reason.shouldBeEmpty()
            }
        }
    }
})