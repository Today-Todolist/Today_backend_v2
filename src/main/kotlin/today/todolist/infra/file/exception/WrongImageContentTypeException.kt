package today.todolist.infra.file.exception

import today.todolist.global.error.ErrorCode.WRONG_IMAGE_CONTENT_TYPE
import today.todolist.global.error.exception.SimpleException

class WrongImageContentTypeException (contentType: String): SimpleException(
    WRONG_IMAGE_CONTENT_TYPE, "The image content type must be image/png or image/jpeg. But image content type is $contentType"
)