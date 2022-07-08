package today.todolist.global.security.auth

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import today.todolist.domain.user.dao.UserRepository
import today.todolist.global.security.exception.InvalidTokenException

@Service
class AuthDetailsService (
    val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        if (userRepository.existsById(username)) AuthDetails(username)
            else throw InvalidTokenException()
}