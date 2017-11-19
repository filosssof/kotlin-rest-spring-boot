package md.fiodorov

import md.fiodorov.entity.Answer
import md.fiodorov.entity.Author
import md.fiodorov.entity.Question
import md.fiodorov.entity.Role
import md.fiodorov.repository.AnswerRepository
import md.fiodorov.repository.AuthorRepository
import md.fiodorov.repository.QuestionRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

/**
 * @author rfiodorov
 * on 22/08/17.
 */
@SpringBootApplication
class HzApplication {

    @Bean
    fun init(questionRepository: QuestionRepository, authorRepository: AuthorRepository, answerRepository: AnswerRepository) = CommandLineRunner {
        val author = authorRepository.save(Author(name = "Test", email = "Email@example.com", password = "123123", roles = Role.GUEST))
        questionRepository.save(Question(title = "Title", content = "Content", createdBy = author))
        questionRepository.save(Question(title = "Корейский вопрос", content = "Интернетовская шутка: в Сети появился корейский вирус. Каково его действие?", createdBy = author))
        val question = questionRepository.save(Question(title = "Эйнштейнов вопрос", content = "А стоит ли носить носки", createdBy = author))

        val answer = Answer(content="Гладиолус?", questionId = 3, createdBy = author)
        answerRepository.save(answer)
        question.answers.add(answer)
        questionRepository.save(question)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(HzApplication::class.java, *args)
}
