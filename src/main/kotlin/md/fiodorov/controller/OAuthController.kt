package md.fiodorov.controller

import md.fiodorov.security.userdetails.FacebookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.IOException
import javax.servlet.http.HttpServletResponse

/**
 * @author rfiodorov
 * on 28/11/17.
 */
@RequestMapping("/oauth")
@RestController
class OAuthController(private val facebookService: FacebookService) {

    @RequestMapping(value = "/facebook/login", method = arrayOf(RequestMethod.POST))
    @ResponseStatus(HttpStatus.OK)
    @Throws(IOException::class)
    fun loginFacebookClient(@RequestBody
                            accessToken: String, response: HttpServletResponse): ResponseEntity<Void> {
        facebookService.loginFacebook(accessToken, response)
        return ResponseEntity(HttpStatus.OK)
    }

}
