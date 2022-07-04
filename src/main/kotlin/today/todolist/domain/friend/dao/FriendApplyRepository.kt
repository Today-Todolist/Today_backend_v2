package today.todolist.domain.friend.dao

import org.springframework.data.mongodb.repository.MongoRepository
import today.todolist.domain.friend.domain.FriendApply
import java.util.*

interface FriendApplyRepository: MongoRepository<FriendApply, UUID>