package md.fiodorov.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import md.fiodorov.entity.Answer
import md.fiodorov.entity.Author
import md.fiodorov.entity.Question
import md.fiodorov.filter.AnswerFilter
import md.fiodorov.filter.QuestionFilter
import md.fiodorov.repository.AnswerRepository
import md.fiodorov.repository.AuthorRepository
import md.fiodorov.repository.QuestionRepository
import md.fiodorov.validation.NotNullOrNegative
import md.fiodorov.view.CreateUpdateQuestionView
import md.fiodorov.view.ShowQuestionView
import md.fiodorov.view.toShowQuestionView
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.time.Instant
import javax.persistence.EntityNotFoundException

/**
 * @author rfiodorov
 * on 19/09/17.
 */
@Service
@Validated
class QuestionService(val questionRepository: QuestionRepository, val authorRepository: AuthorRepository, val answerRepository: AnswerRepository) {

    fun findByFilter(filterString: String?, pageRequest: Pageable): Page<Question> {
        return if (filterString.isNullOrBlank()) {
            questionRepository.findAll(pageRequest)
        } else {
            val filter: QuestionFilter = ObjectMapper().readValue(filterString as String)
            questionRepository.findAll(filter.toSpecification(), pageRequest)
        }
    }

    fun findById(@NotNullOrNegative id: Long): ShowQuestionView? {
        val domain = questionRepository.findOneByIdAndDeletedFalse(id)
        return domain?.toShowQuestionView()
    }

    fun add(view: CreateUpdateQuestionView, currentUser: Author = Author(name = "NoName", email = "noname@example.com")) {//TODO remove default user after including user management
        val savedAuthor = authorRepository.save(currentUser)//TODO also remove this logic after real user login
        val question = Question(title = view.title, content = view.content, createdBy = savedAuthor)
        questionRepository.save(question)
    }

    fun delete(@NotNullOrNegative id: Long) {
        val storedQuestion = questionRepository.findOne(id) ?: throw EntityNotFoundException("The question with this id does not exist")
        storedQuestion.deleted = true
        questionRepository.save(storedQuestion)
    }

    fun edit(@NotNullOrNegative id: Long, view: CreateUpdateQuestionView, currentUser: Author = Author(name = "NoName", email = "noname@example.com")) {//TODO remove default user after including user management
        val savedAuthor = authorRepository.save(currentUser)//TODO also remove this logic after real user login
        val storedQuestion = questionRepository.findOne(id) ?: throw EntityNotFoundException("The question with this id does not exist")
        processUpdateQuestion(view, storedQuestion, savedAuthor)
        questionRepository.save(storedQuestion)
    }

    fun getAnswersByQuestionId(@NotNullOrNegative questionId: Long,  pageRequest: Pageable): Page<Answer> {
        val filter = AnswerFilter(questionId = questionId)
        return answerRepository.findAll(filter.toSpecification(), pageRequest)
    }

    fun addAnswerForQuestion(@NotNullOrNegative questionId: Long, content: String, userName: String){
        val author = authorRepository.findOneByNameAndDeletedFalse(userName)
        val dbAuthor = authorRepository.save(author)
        if(dbAuthor!=null){
            val answer = Answer(questionId,content, dbAuthor)
            answerRepository.save(answer)
        }
    }

    private fun processUpdateQuestion(view: CreateUpdateQuestionView, domain: Question, editor: Author) {
        if (domain.title != view.title) {
            domain.title = view.title
        }

        if (domain.content != view.content) {
            domain.content = view.content
        }

        domain.editedDate = Instant.now()
        domain.editedBy = editor
    }
}
