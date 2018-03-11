package md.fiodorov.view

import com.fasterxml.jackson.annotation.JsonCreator
import md.fiodorov.entity.Answer
import md.fiodorov.entity.Author
import md.fiodorov.entity.Question
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

data class ShowAnswerView constructor(
        val id: Long?,
        val content: String,
        val rank: Int,
        val questionId: Long?,
        val createdBy: ShowAuthorView,
        val createdDate: Instant,
        val editedBy: ShowAuthorView?,
        val editedDate: Instant?
)

data class ShowAuthorView constructor(
        val id: Long?,
        val name: String,
        val karma: Int,
        val facebookId: String?
)

data class CreateUpdateAnswerView constructor(
        val content: String
)

fun Author.toShowAuthorView() = ShowAuthorView(
        id = this.id,
        name = this.name,
        karma = this.karma,
        facebookId = this.facebookId)

fun Question.toShowQuestionView() = ShowQuestionView(
        id = this.id,
        title = this.title,
        content = this.content,
        rank = this.rank,
        createdBy = this.createdBy.toShowAuthorView(),
        createdDate = this.createdDate,
        editedDate = this.editedDate,
        editedBy = this.editedBy?.toShowAuthorView(),
        answered = this.answered
)

fun Answer.toShowAnswerView() = ShowAnswerView(
        id = this.id,
        content = this.content,
        rank = this.rank,
        questionId = this.questionId,
        createdBy = this.createdBy.toShowAuthorView(),
        createdDate = this.createdDate,
        editedDate = this.editedDate,
        editedBy = this.editedBy?.toShowAuthorView()
)

