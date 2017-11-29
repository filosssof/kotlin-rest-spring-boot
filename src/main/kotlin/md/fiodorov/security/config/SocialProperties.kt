package md.fiodorov.security.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * @author rfiodorov
 * on 29/11/17.
 */
@Component
@ConfigurationProperties(prefix = "spring.social")
public class SocialProperties {

    data class Facebook(val appId:String, val appSecret: String)
}
