package md.fiodorov.filter

import au.com.console.jpaspecificationdsl.*
import md.fiodorov.entity.Answer
import org.springframework.data.jpa.domain.Specifications
import java.time.Instant

/**
 * @author rfiodorov
 * on 29/09/17.
 */
class AnswerFilter(
        val startCreatedDate: Instant? = null,
        val endCreatedDate: Instant? = null,
        val minRank: Int? = null,
        val maxRank: Int? = null,
        val contentKeyword: String? = null,
        val questionId: Long? = null,
        val deleted: Boolean = false
        ){

    fun toSpecification(): Specifications<Answer> = and(
            isGreaterThanStartDate(),
            isLessThanEndDate(),
            hasRankGreater(),
            hasRankLess(),
            hasKeywordContent(),
            isDeleted(),
            hasQuestionId()
    )

    private fun isGreaterThanStartDate(): Specifications<Answer>? = this.startCreatedDate?.let {
        Answer::createdDate.greaterThanOrEqualTo(it)
    }

    private fun isLessThanEndDate(): Specifications<Answer>? = this.endCreatedDate?.let {
        Answer::createdDate.lessThanOrEqualTo(it)
    }

    private fun hasRankGreater(): Specifications<Answer>? = this.minRank?.let {
        Answer::rank.greaterThanOrEqualTo(it)
    }

    private fun hasRankLess(): Specifications<Answer>? = this.maxRank?.let {
        Answer::rank.lessThanOrEqualTo(it)
    }

    private fun hasKeywordContent(): Specifications<Answer>? = this.contentKeyword?.let {
        Answer::content.like("%$it%")
    }

    private fun isDeleted(): Specifications<Answer> = this.deleted.let {
        Answer::deleted.equal(it)
    }

    private fun hasQuestionId(): Specifications<Answer> = this.questionId.let {
        Answer::questionId.equal(it)
    }
}
