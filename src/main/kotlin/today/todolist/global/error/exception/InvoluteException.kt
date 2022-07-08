package today.todolist.global.error.exception

import today.todolist.global.error.ErrorCode
import today.todolist.global.error.dto.InvoluteErrorResponse

open class InvoluteException (
    errorCode: ErrorCode,
    reason: Map<String, String>
): RuntimeException (errorCode.message), GlobalException<InvoluteErrorResponse> {
    private val errorResponse: InvoluteErrorResponse = InvoluteErrorResponse(errorCode, reason)
    override fun toErrorResponse(): InvoluteErrorResponse = errorResponse

    @Synchronized
    override fun fillInStackTrace(): Throwable? = null
}