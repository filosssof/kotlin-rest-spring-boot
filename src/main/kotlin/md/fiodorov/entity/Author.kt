package md.fiodorov.entity

import org.springframework.data.annotation.PersistenceConstructor
import java.time.Instant
import javax.persistence.*


/**
 * @author rfiodorov
 * on 19/09/17.
 */
@Entity
data class Author @PersistenceConstructor constructor(@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
                                                      val id: Long? = null,
                                                      val name: String,
                                                      val email: String,
                                                      val registeredDate: Instant = Instant.now(),
                                                      var karma: Int = 0,
                                                      var deleted: Boolean = false,
                                                      @Enumerated(EnumType.STRING)
                                                      var roles: Role = Role.GUEST,
                                                      var facebookId: String? = null,
                                                      var googleId: String? = null

)

enum class Role {
    GUEST,
    ADMIN,
    MODERATOR
}


