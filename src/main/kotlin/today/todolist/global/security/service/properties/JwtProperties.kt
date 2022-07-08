package today.todolist.global.security.service.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.util.*

@ConstructorBinding
@ConfigurationProperties(prefix = "auth.jwt")
class JwtProperties (
    secret: String,
    accessExp: Long,
    refreshExp: Long
) {
    val secret: String = Base64.getEncoder().encodeToString(secret.toByteArray())
    val accessExp: Long = accessExp * 1000L
    val refreshExp: Long = refreshExp * 1000L
}