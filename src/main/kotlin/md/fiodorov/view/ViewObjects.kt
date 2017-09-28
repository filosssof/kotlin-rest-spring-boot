package md.fiodorov.view

import com.fasterxml.jackson.annotation.JsonCreator
import md.fiodorov.entity.Author
import md.fiodorov.entity.Question
import md.fiodorov.utils.GravatarUtils
import org.hibernate.validator.constraints.NotBlank
import java.time.Instant

/**
 * @author rfiodorov
 * on 22/08/17.
 */
data class CreateUpdateQuestionView @JsonCreator constructor(
        @get:NotBlank val title: String,
        @get:NotBlank val content: String
)

data class ShowQuestionView constructor(
        val id: Long?,
        val title: String,
        val content: String,
        val rank: Int,
        val answered: Boolean,
        val createdBy: ShowAuthorView,
        val createdDate: Instant,
        val editedBy: ShowAuthorView?,
        val editedDate: Instant?
)

data class ShowAuthorView constructor(
        val id: Long?,
        val name: String,
        val avatarSource: String,
        val karma: Int
)

fun Author.toShowAuthorview() = ShowAuthorView(
        id = this.id,
        name = this.name,
        karma = this.karma,
        avatarSource = GravatarUtils.getImgSourceByGravar(this.email))

fun Question.toShowQuestionView() = ShowQuestionView(
        id = this.id,
        title = this.title,
        content = this.content,
        rank = this.rank,
        createdBy = this.createdBy.toShowAuthorview(),
        createdDate = this.createdDate,
        editedDate = this.editedDate,
        editedBy = this.editedBy?.toShowAuthorview(),
        answered = this.answered
)
