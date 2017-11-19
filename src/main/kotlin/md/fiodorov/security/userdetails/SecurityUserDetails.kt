package md.fiodorov.security.userdetails

import md.fiodorov.entity.Author
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * @author rfiodorov
 * on 19/11/17.
 */
class SecurityUserDetails(val user: Author) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority>
            = mutableListOf<GrantedAuthority>(SimpleGrantedAuthority(user.roles.name))

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
