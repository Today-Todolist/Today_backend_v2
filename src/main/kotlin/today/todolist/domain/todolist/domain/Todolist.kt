package today.todolist.domain.todolist.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import today.todolist.domain.todolist.domain.inner.TodolistSubjectInfo
import java.time.LocalDate

@Document
class Todolist (
    @Id val userEmail: String
) {
    var date: Map<LocalDate, List<TodolistSubjectInfo>> = mapOf()
        private set
}