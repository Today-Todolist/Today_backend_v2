package today.todolist.global.domain.inner

import java.time.LocalDate

class FriendInfo (
    val friendEmail: String
) {
    var createdAt: LocalDate = LocalDate.now()
        private set
}