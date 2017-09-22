package md.fiodorov.filter

import java.time.Instant

/**
 * @author rfiodorov
 * on 22/09/17.
 */
data class QuestionFilter(
        val startCreatedDate: Instant?,
        val endCreatedDate: Instant?,
        val minRank: Int?,
        val maxRank: Int?,
        val title: String?,
        val content: String?,
        val answered: Boolean?)
