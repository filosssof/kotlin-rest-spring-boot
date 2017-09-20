package md.fiodorov.entity

import java.time.Instant
import javax.persistence.*

/**
 * @author rfiodorov
 * on 19/09/17.
 */
@MappedSuperclass
open class BaseModel() {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    var createdDate: Instant = Instant.now()

    @ManyToOne
    lateinit var createdBy: Author

    var editedDate: Instant? = null

    open var content: String = ""

    var rank: Int = 0
}
