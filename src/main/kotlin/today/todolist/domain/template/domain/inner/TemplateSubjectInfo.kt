package today.todolist.domain.template.domain.inner

import today.todolist.domain.todolist.domain.inner.TodolistContentInfo
import today.todolist.domain.todolist.domain.inner.TodolistSubjectInfo
import java.util.*

class TemplateSubjectInfo (
    val subject: String
) {
    var id: UUID = UUID.randomUUID()
        private set

    var contents: MutableList<TemplateContentInfo> = mutableListOf()
        private set

    fun getTodolist(): TodolistSubjectInfo = TodolistSubjectInfo(
        subject = subject,
        contents = contents.map {
                content -> TodolistContentInfo(content.content)
        }.toMutableList()
    )
}