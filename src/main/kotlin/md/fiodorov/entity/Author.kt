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
                                                      @JsonIgnore val password: String,
                                                      val registeredDate: Instant = Instant.now(),
                                                      val karma: Int = 0,
                                                      val deleted: Boolean = false,
                                                      @Enumerated(EnumType.STRING)
                                                      val roles: Role = Role.GUEST

)

enum class Role {
    GUEST,
    ADMIN,
    MODERATOR
}


