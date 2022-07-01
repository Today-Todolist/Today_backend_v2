package today.todolist.domain.todolist.domain.inner

import java.util.*

class TodolistSubjectInfo (
    val subject: String,
    val contents: MutableList<TodolistContentInfo> = mutableListOf()
) {
    var id: UUID = UUID.randomUUID()
        private set
}