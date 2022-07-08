package today.todolist.global.error.exception

import today.todolist.global.error.ErrorCode
import today.todolist.global.error.dto.BasicErrorResponse

open class BasicException (
    errorCode: ErrorCode
): RuntimeException (errorCode.message), GlobalException<BasicErrorResponse> {
    private val errorResponse: BasicErrorResponse = BasicErrorResponse(errorCode)
    override fun toErrorResponse(): BasicErrorResponse = errorResponse

    @Synchronized
    override fun fillInStackTrace(): Throwable? = null
}