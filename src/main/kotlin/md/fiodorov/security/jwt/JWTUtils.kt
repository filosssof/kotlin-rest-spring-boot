package md.fiodorov.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import md.fiodorov.entity.Author
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*
import java.util.concurrent.TimeUnit
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author rfiodorov
 * on 19/11/17.
 */
internal object JWTUtils {
    private val expiration: Long = 100L
    private val secret = "Hail Hitler"
    private val header = "Authorization"

    private val logger: Logger = LoggerFactory.getLogger(JWTUtils::class.java)

    private fun Author.createJwt(): String {
        val claims = HashMap<String, Any>()
        claims.put("roles", this.roles)
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(this.email)
                .setExpiration(Date(Date().time + TimeUnit.HOURS.toMillis(expiration)))
                .signWith(SignatureAlgorithm.HS256, secret).compact()
    }

    fun addAuthentication(response: HttpServletResponse, user: Author) {
        val jwt = user.createJwt()
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200")
        val jwtView = JwtView(jwt,user.name)
        response.characterEncoding = "utf-8"
        response.writer.write(jwtView.toJson())
        response.writer.flush()
        response.writer.close()
    }

    fun getAuthentication(request: HttpServletRequest): Authentication? {
        val token = request.getHeader(header) ?: return null

        val tokenBody = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .body

        val username: String = tokenBody.subject
        @Suppress("UNCHECKED_CAST")
        val roles = tokenBody["roles"] as List<String>

        val res = roles.mapTo(LinkedList<GrantedAuthority>()) { SimpleGrantedAuthority(it) }

        logger.info(username + " logged in with authorities " + res)
        return UsernamePasswordAuthenticationToken(username, null, res)
    }
}

class JwtView(val token: String, val username: String)
fun JwtView.toJson(): String? = ObjectMapper().writeValueAsString(this)
