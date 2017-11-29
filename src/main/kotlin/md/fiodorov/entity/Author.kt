package md.fiodorov.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.PersistenceConstructor
import java.time.Instant
import javax.persistence.*


/**
 * @author rfiodorov
 * on 19/09/17.
 */
@Entity
data class Author @PersistenceConstructor constructor(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
                                                      val name: String,
                                                      val email: String,
                                                      @JsonIgnore var password: String? = null,
                                                      val registeredDate: Instant = Instant.now(),
                                                      var karma: Int = 0,
                                                      var deleted: Boolean = false,
                                                      @Enumerated(EnumType.STRING)
                                                      var roles: Role = Role.GUEST,
                                                      var avatarUrl:String? = null,
                                                      var facebookId: String? = null

)

enum class Role {
    GUEST,
    ADMIN,
    MODERATOR
}


