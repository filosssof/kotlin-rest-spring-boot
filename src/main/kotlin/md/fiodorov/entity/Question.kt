package md.fiodorov.entity

import javax.persistence.Entity

/**
 * @author rfiodorov
 * on 19/09/17.
 */
@Entity
class Question(
        var title: String,

        val answered: Boolean = false,

        override
        var content: String,

        override
        var createdBy: Author

) : BaseModel(title, content, createdBy)
