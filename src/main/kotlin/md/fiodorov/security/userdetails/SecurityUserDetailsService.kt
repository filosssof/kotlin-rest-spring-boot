package md.fiodorov.security.userdetails

import md.fiodorov.entity.Author
import md.fiodorov.repository.AuthorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * @author rfiodorov
 * on 19/11/17.
 */
@Service
class SecurityUserDetailsService @Autowired
constructor(private val userRepo: AuthorRepository) : UserDetailsService {
    override fun loadUserByUsername(s: String): UserDetails {
        val user: Author = userRepo.findOneByEmailAndDeletedFalse(s) ?: throw(UsernameNotFoundException("Username not found or it is deleted"))
        return SecurityUserDetails(user)
    }
}
