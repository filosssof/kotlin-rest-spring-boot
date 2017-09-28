package md.fiodorov.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import md.fiodorov.entity.Author
import md.fiodorov.entity.Question
import md.fiodorov.filter.QuestionFilter
import md.fiodorov.repository.AuthorRepository
import md.fiodorov.repository.QuestionRepository
import md.fiodorov.validation.NotNullOrNegative
import md.fiodorov.view.CreateQuestionView
import md.fiodorov.view.ShowQuestionView
import md.fiodorov.view.toShowQuestionView
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

/**
 * @author rfiodorov
 * on 19/09/17.
 */
@Service
@Validated
class QuestionService(val questionRepository: QuestionRepository, val authorRepository: AuthorRepository) {

    fun findByFilter(filterString: String?, pageRequest: Pageable): Iterable<Question> {
        return if (filterString.isNullOrBlank()) {
            questionRepository.findAll(pageRequest)
        } else {
            val filter: QuestionFilter = ObjectMapper().readValue(filterString as String)
            questionRepository.findAll(filter.toSpecification(), pageRequest)
        }
    }

    fun findById(@NotNullOrNegative id: Long): ShowQuestionView? {
            val domain = questionRepository.findOne(id)
            return domain?.toShowQuestionView()
    }

    fun save(view: CreateQuestionView, currentUser: Author = Author(name = "NoName", email = "noname@example.com")) {//TODO remove default user after including user management
        val savedAuthor = authorRepository.save(currentUser)//TODO also remove this logic after real user login
        val question = Question(title = view.title, content = view.content, createdBy = savedAuthor)
        questionRepository.save(question)
    }
}
