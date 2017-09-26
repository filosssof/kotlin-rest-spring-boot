package md.fiodorov.filter

import au.com.console.jpaspecificationdsl.*
import md.fiodorov.entity.Question
import org.springframework.data.jpa.domain.Specifications
import java.time.Instant

/**
 * @author rfiodorov
 * on 22/09/17.
 */
data class QuestionFilter(
        val startCreatedDate: Instant? = null,
        val endCreatedDate: Instant? = null,
        val minRank: Int? = null,
        val maxRank: Int? = null,
        val titleKeyword: String? = null,
        val contentKeyword: String? = null,
        val answered: Boolean? = null) {

    fun toSpecification(): Specifications<Question> = and(
            isGreaterThanStartDate(),
            isLessThanEndDate(),
            hasRankGreater(),
            hasRankLess(),
            hasKeywordTitle(),
            hasKeywordContent(),
            isAnswered()
    )

    private fun isGreaterThanStartDate(): Specifications<Question>? = this.startCreatedDate?.let {
        Question::createdDate.greaterThanOrEqualTo(this.startCreatedDate)
    }

    private fun isLessThanEndDate(): Specifications<Question>? = this.endCreatedDate?.let {
        Question::createdDate.lessThanOrEqualTo(this.endCreatedDate)
    }

    private fun hasRankGreater(): Specifications<Question>? = this.minRank?.let {
        Question::rank.greaterThanOrEqualTo(this.minRank)
    }

    private fun hasRankLess(): Specifications<Question>? = this.maxRank?.let {
        Question::rank.lessThanOrEqualTo(maxRank)
    }

    private fun hasKeywordTitle(): Specifications<Question>? = this.titleKeyword?.let {
        Question::title.like("%$titleKeyword%")
    }

    private fun hasKeywordContent(): Specifications<Question>? = this.contentKeyword?.let {
        Question::content.like("%$contentKeyword%")
    }

    private fun isAnswered(): Specifications<Question>? = this.answered?.let {
        Question::answered.equal(this.answered)
    }
}
