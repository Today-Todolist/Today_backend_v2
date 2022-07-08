package today.todolist.global.security.filter

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import today.todolist.global.security.service.JwtProvider
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenFilter (
    private val jwtProvider: JwtProvider
): OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        jwtProvider.resolveToken(request)
            ?.let { token -> jwtProvider.getBody(token) }
            ?.let { claim -> jwtProvider.getAuthentication(claim) }
            ?.also { authentication -> SecurityContextHolder.getContext().authentication = authentication }
        chain.doFilter(request, response)
    }
}