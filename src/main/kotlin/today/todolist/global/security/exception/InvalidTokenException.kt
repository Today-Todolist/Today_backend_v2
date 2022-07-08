package today.todolist.global.security.exception

import today.todolist.global.error.ErrorCode.INVALID_TOKEN
import today.todolist.global.error.exception.BasicException

class InvalidTokenException: BasicException (
    INVALID_TOKEN
)