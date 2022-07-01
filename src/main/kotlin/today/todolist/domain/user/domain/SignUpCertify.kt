package today.todolist.domain.user.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.redis.core.RedisHash

@RedisHash(timeToLive = 300)
class SignUpCertify (
    @field:Indexed val email: String,
    val password: String,
    @field:Indexed val nickname: String
) {
    @Id
    var id: Long = getId()
        private set

    companion object {
        private var id: Long = 0
        private fun getId(): Long = id++
    }
}