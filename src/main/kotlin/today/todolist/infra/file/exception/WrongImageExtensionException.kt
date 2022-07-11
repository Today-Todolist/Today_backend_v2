package today.todolist.infra.file.exception

import today.todolist.global.error.ErrorCode.WRONG_IMAGE_EXTENSION
import today.todolist.global.error.exception.SimpleException

class WrongImageExtensionException (extension: String): SimpleException(
    WRONG_IMAGE_EXTENSION, "The image extension must be jpeg or png or jpg. But image extension is $extension"
)