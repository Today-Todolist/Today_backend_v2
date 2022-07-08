package today.todolist.global.security.service

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import today.todolist.global.security.auth.AuthDetails

@Component
class AuthenticationFacade {
    fun getUserId(): String = (SecurityContextHolder.getContext().authentication.principal as AuthDetails).id
}