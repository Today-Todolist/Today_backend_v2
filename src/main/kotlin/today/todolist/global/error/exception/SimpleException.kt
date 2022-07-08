package today.todolist.global.error.exception

import today.todolist.global.error.ErrorCode
import today.todolist.global.error.dto.SimpleErrorResponse

open class SimpleException (
    errorCode: ErrorCode,
    reason: String
): RuntimeException (errorCode.message), GlobalException<SimpleErrorResponse> {
    private val errorResponse: SimpleErrorResponse = SimpleErrorResponse(errorCode, reason)
    override fun toErrorResponse(): SimpleErrorResponse = errorResponse

    @Synchronized
    override fun fillInStackTrace(): Throwable? = null
}