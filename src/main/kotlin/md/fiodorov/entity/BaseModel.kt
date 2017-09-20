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

    var editedDate: Instant? = null

    @ManyToOne
    open lateinit var createdBy: Author

    open var content: String = ""

    var rank: Int = 0

    constructor(title: String, content: String, createdBy: Author) : this()
}
