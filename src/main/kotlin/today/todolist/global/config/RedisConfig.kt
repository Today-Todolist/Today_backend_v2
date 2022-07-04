package today.todolist.global.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import redis.embedded.RedisServer
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Configuration
@EnableRedisRepositories
class RedisConfig (
    @Value("\${spring.redis.port}") private val port: Int
) {
    @PostConstruct
    fun runRedis() {
        if (redisServer == null) {
            redisServer = RedisServer(port)
            redisServer!!.start()
        }
    }

    @PreDestroy
    fun stopRedis() {
        redisServer?.let { redisServer: RedisServer-> redisServer.stop() }
    }

    companion object {
        private var redisServer: RedisServer? = null
    }
}