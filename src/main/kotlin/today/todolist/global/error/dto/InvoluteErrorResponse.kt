package today.todolist.global.error.dto

import today.todolist.global.error.ErrorCode

class InvoluteErrorResponse (
    errorCode: ErrorCode,
    val reason: Map<String, String>
): BasicErrorResponse (errorCode)