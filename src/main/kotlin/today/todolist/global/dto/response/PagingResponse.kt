package today.todolist.global.dto.response

class PagingResponse<T> (
    val totalElements: Long,
    val contents: MutableList<T>
)