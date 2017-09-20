package md.fiodorov.entity

import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * @author rfiodorov
 * on 19/09/17.
 */
@Entity
data class Author(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
                  val name: String,
                  val email: String,
                  val registeredDate: Instant,
                  val karma: Int
)
