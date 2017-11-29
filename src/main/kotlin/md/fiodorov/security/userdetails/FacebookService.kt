package md.fiodorov.security.userdetails

import md.fiodorov.entity.Author
import md.fiodorov.repository.AuthorRepository
import md.fiodorov.security.jwt.JWTUtils
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.User
import org.springframework.social.facebook.api.impl.FacebookTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException
import javax.servlet.http.HttpServletResponse

/**
 * @author rfiodorov
 * on 28/11/17.
 */
@Service
class FacebookService(val authorRepository: AuthorRepository) {
//    fun signupFacebook()

    @Transactional
    fun loginFacebook(accessToken: String?, response: HttpServletResponse) {
        if(accessToken==null){
            throw IllegalArgumentException()
        }
        val userProfile:User = getFacebookUser(accessToken)
        var author: Author? = authorRepository.findOneByFacebookIdAndDeletedFalse(userProfile.id)
        if(author==null){
            author = authorRepository.findOneByEmailAndDeletedFalse(userProfile.email)
            if(author!=null){
                author.facebookId = userProfile.id
                authorRepository.save(author)
                JWTUtils.addAuthentication(response,author)
            }else{
                throw EntityNotFoundException()
            }
        }else{
            JWTUtils.addAuthentication(response,author)
        }
    }

    private fun getFacebookUser(accessToken: String): User {
        val facebook: Facebook = FacebookTemplate(accessToken)
        return facebook.userOperations().userProfile
    }


}
