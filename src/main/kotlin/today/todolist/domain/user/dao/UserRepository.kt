package today.todolist.domain.user.dao

import org.springframework.data.mongodb.repository.MongoRepository
import today.todolist.domain.user.domain.User

interface UserRepository: MongoRepository<User, String>