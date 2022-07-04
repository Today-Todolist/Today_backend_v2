package today.todolist.global.error.exception

interface GlobalException<T> {
    fun toErrorResponse(): T
}