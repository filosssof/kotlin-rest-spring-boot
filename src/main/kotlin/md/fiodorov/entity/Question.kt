package md.fiodorov.entity

import javax.persistence.Entity

/**
 * @author rfiodorov
 * on 19/09/17.
 */
@Entity
class Question(val title: String, override var content: String, override var createdBy: Author) : BaseModel(title,content, createdBy)
