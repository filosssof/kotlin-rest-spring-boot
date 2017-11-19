package md.fiodorov.entity

import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import javax.persistence.Entity
import javax.persistence.OneToMany

/**
 * @author rfiodorov
 * on 19/09/17.
 */
@Entity
class Question(
        var title: String,

        val answered: Boolean = false,

        @OneToMany
        @LazyCollection(LazyCollectionOption.EXTRA)
        val answers: MutableList<Answer> = mutableListOf(),

        override
        var content: String,

        override
        var createdBy: Author

) : BaseModel(title, content, createdBy)
