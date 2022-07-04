package today.todolist.domain.template.dao

import org.springframework.data.mongodb.repository.MongoRepository
import today.todolist.domain.template.domain.Template

interface TemplateRepository: MongoRepository<Template, String>