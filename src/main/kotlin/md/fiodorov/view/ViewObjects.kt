package md.fiodorov.view

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * @author rfiodorov
 * on 22/08/17.
 */
data class CreateQuestionView @JsonCreator constructor(
        val title: String,
        val content: String
       )

