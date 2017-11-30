package md.fiodorov.security.userdetails

import md.fiodorov.entity.Author
import md.fiodorov.repository.AuthorRepository
import md.fiodorov.security.jwt.JWTUtils
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.User
import org.springframework.social.facebook.api.impl.FacebookTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.servlet.http.HttpServletResponse

/**
 * @author rfiodorov
 * on 28/11/17.
 */
@Service
class FacebookService(val authorRepository: AuthorRepository) {

    @Transactional
    fun loginFacebook(accessToken: String?, response: HttpServletResponse) {
        if (accessToken == null) {
            throw IllegalArgumentException()
        }
        val userProfile: User = getFacebookUser(accessToken)
        var author: Author? = authorRepository.findOneByFacebookIdAndDeletedFalse(userProfile.id)
        if (author == null) {
            author = authorRepository.findOneByEmailAndDeletedFalse(userProfile.email)
            if (author != null) {
                author.facebookId = userProfile.id
                authorRepository.save(author)
            } else {
                author = createNewFacebookUser(userProfile)
            }
        }
        JWTUtils.addAuthentication(response, author)
    }

    private fun createNewFacebookUser(userProfile: User): Author {
        val author = userProfile.toAuthor()
        return authorRepository.save(author)
    }

    private fun getFacebookUser(accessToken: String): User {
        val facebook: Facebook = FacebookTemplate(accessToken)
        return facebook.userOperations().userProfile
    }

    private fun User.toAuthor() = Author(
            name = "${this.firstName}  ${this.lastName}",
            email = this.email,
            facebookId = this.id
    )

}
