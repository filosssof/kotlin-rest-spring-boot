package md.fiodorov.repository

import md.fiodorov.entity.Answer
import md.fiodorov.entity.Author
import md.fiodorov.entity.Question
import org.springframework.data.repository.CrudRepository

/**
 * @author rfiodorov
 * on 22/08/17.
 */

interface QuestionRepository: CrudRepository<Question, Long>

interface AnswerRepository: CrudRepository<Answer, Long>

interface AuthorRepository: CrudRepository<Author, Long>



