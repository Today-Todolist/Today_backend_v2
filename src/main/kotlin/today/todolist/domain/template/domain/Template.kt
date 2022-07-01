package today.todolist.domain.template.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import today.todolist.domain.template.domain.inner.TemplateSubjectInfo
import java.time.LocalDate

@Document
class Template (
    @Id val userEmail: String,
    val size: Int,
    val title: String,
    val profile: String
) {
    var day: Map<Int, List<TemplateSubjectInfo>> = mapOf()
        private set

    var createdAt: LocalDate = LocalDate.now()
        private set
}