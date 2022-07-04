package today.todolist.domain.user.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.util.concurrent.atomic.AtomicInteger

@RedisHash(timeToLive = 300)
class SignUpCertify (
    @Indexed val email: String,
    val password: String,
    @Indexed val nickname: String
) {
    @Id
    var id: Int = getId()
        private set

    companion object {
        private var count: AtomicInteger = AtomicInteger()
        private fun getId(): Int = count.incrementAndGet()
    }
}