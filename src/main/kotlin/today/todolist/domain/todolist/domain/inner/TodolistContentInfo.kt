package today.todolist.domain.todolist.domain.inner

import java.util.*

class TodolistContentInfo (
    val content: String
) {
    var id: UUID = UUID.randomUUID()
        private set

    var success: Boolean = false
}