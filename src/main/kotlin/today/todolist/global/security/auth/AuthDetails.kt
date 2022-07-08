package today.todolist.global.security.auth

import org.springframework.security.core.GrantedAuthority

import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class AuthDetails (
    val id: String
): UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority?> = Collections.emptyList()
    override fun getPassword(): String? = null
    override fun getUsername(): String? = null
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}