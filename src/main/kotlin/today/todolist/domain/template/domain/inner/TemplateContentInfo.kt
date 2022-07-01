package today.todolist.domain.template.domain.inner

import java.util.*

class TemplateContentInfo (
    val content: String
) {
    var id: UUID = UUID.randomUUID()
        private set
}