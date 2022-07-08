package today.todolist.global.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import today.todolist.global.security.service.JwtProvider

class FilterConfig (
    private val jwtProvider: JwtProvider,
    private val objectMapper: ObjectMapper
): SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
    override fun configure(builder: HttpSecurity) {
        builder.addFilterBefore(JwtTokenFilter(jwtProvider), UsernamePasswordAuthenticationFilter::class.java)
        builder.addFilterBefore(ExceptionHandlerFilter(objectMapper), JwtTokenFilter::class.java)
        builder.addFilterBefore(LogFilter(), ExceptionHandlerFilter::class.java)
    }
}