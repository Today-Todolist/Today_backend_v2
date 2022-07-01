package today.todolist.domain.friend.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import today.todolist.global.domain.inner.FriendInfo
import java.util.*

@Document
class FriendApply (
    val userEmail: String,
    friendEmail: String
) {
    @Id
    var id: UUID = UUID.randomUUID()
        private set

    var friendInfo: FriendInfo = FriendInfo(friendEmail)
        private set
}