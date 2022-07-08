package today.todolist.global.security.service

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component
import today.todolist.global.security.auth.AuthDetailsService
import today.todolist.global.security.exception.InvalidTokenException
import today.todolist.global.security.service.properties.JwtProperties
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtProvider (
    private val jwtProperties: JwtProperties,
    private val authDetailsService: AuthDetailsService
) {
    fun generateAccessToken(id: String): String = makingToken(id, "access", jwtProperties.accessExp)
    fun generateRefreshToken(id: String): String = makingToken(id, "refresh", jwtProperties.refreshExp)

    private fun makingToken(id: String, type: String, time: Long): String =
        Jwts.builder()
            .setExpiration(Date(System.currentTimeMillis() + time))
            .signWith(SignatureAlgorithm.HS512, jwtProperties.secret)
            .setIssuedAt(Date())
            .setSubject(id)
            .claim("type", type)
            .compact()

    fun resolveToken(request: HttpServletRequest): String? =
        request.getHeader("Authorization")
            ?.takeIf { bearerToken -> bearerToken.startsWith("Bearer ") }
            ?.let { bearerToken -> bearerToken.substring(7) }

    fun getAuthentication(body: Claims): Authentication {
        if (!isAccess(body)) throw InvalidTokenException()
        val details = authDetailsService.loadUserByUsername(getId(body))
        return UsernamePasswordAuthenticationToken(details, "", details.authorities)
    }

    fun getBody(token: String): Claims =
        try {
            Jwts.parser().setSigningKey(jwtProperties.secret).parseClaimsJws(token).body
        } catch (e: Exception) {
            throw InvalidTokenException()
        }

    private fun isAccess(body: Claims): Boolean = body.get("type", String::class.java) == "access"
    fun isRefresh(body: Claims): Boolean = body.get("type", String::class.java) == "refresh"
    fun getId(body: Claims): String = body.subject
}