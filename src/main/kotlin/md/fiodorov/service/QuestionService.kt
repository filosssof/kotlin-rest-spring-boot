package md.fiodorov.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import md.fiodorov.entity.Question
import md.fiodorov.filter.QuestionFilter
import md.fiodorov.repository.QuestionRepository
import md.fiodorov.view.ShowQuestionView
import md.fiodorov.view.toShowQuestionView
import org.jetbrains.annotations.NotNull
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

/**
 * @author rfiodorov
 * on 19/09/17.
 */
@Service
class QuestionService(val questionRepository: QuestionRepository) {

    fun findByFilter(filterString: String?, pageRequest: Pageable): Iterable<Question> {
        return if(filterString.isNullOrBlank()){
            questionRepository.findAll(pageRequest)
        }else{
            val filter: QuestionFilter = ObjectMapper().readValue(filterString as String)
            questionRepository.findAll(filter.toSpecification(), pageRequest)
        }
    }

    fun findById(@NotNull id: Long): ShowQuestionView? {
        if (id > 0) {
            val domain = questionRepository.findOne(id)
            return domain?.toShowQuestionView()
        } else {
            throw IllegalArgumentException("Id should be greater than 0")
        }
    }
}
