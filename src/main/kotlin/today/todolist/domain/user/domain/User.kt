package today.todolist.domain.user.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import today.todolist.global.domain.inner.FriendInfo

@Document
class User (
    @Id val email: String,
    val password: String,
    val nickname: String,
    val profile: String
) {
    var friendEmails: MutableList<FriendInfo> = mutableListOf()
        private set

    var changePossible: Boolean = true
        private set
}
