package md.fiodorov.repository

import md.fiodorov.entity.Answer
import md.fiodorov.entity.Author
import md.fiodorov.entity.Question
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository


/**
 * @author rfiodorov
 * on 22/08/17.
 */

interface QuestionRepository: PagingAndSortingRepository<Question, Long>, JpaSpecificationExecutor<Question>{
    fun findOneByIdAndDeletedFalse(id: Long):Question?
}

interface AnswerRepository: PagingAndSortingRepository<Answer, Long>, JpaSpecificationExecutor<Answer>

interface AuthorRepository: PagingAndSortingRepository<Author, Long>, JpaSpecificationExecutor<Author>{
    fun findOneByNameAndDeletedFalse(name: String):Author?
}




