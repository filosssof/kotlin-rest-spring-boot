package md.fiodorov.entity

import javax.persistence.Entity
import javax.persistence.ManyToOne

/**
 * @author rfiodorov
 * on 19/09/17.
 */

@Entity
class Answer : BaseModel() {
    @ManyToOne
    lateinit var question: Question
}
