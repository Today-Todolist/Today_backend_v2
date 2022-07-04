package today.todolist.global.error.dto

import today.todolist.global.error.ErrorCode

class SimpleErrorResponse (
    errorCode: ErrorCode,
    val reason: String
): BasicErrorResponse (errorCode)