package today.todolist.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsUtils
import today.todolist.global.security.filter.FilterConfig
import today.todolist.global.security.service.JwtProvider


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SecurityConfig (
    private val jwtProvider: JwtProvider,
    private val objectMapper: ObjectMapper
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http.csrf().disable()
            .sessionManagement().disable()
            .formLogin().disable().cors()
            .and().exceptionHandling()
            .and().authorizeRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .anyRequest().permitAll()
            .and().apply<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>>(FilterConfig(jwtProvider, objectMapper))
            .and().build()

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer =
        WebSecurityCustomizer { web -> web.ignoring().antMatchers("/favicon.ico") }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}