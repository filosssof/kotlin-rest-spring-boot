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
class HzApplication{


    @Bean
    fun init(questionRepository: QuestionRepository, authorRepository: AuthorRepository, answerRepository: AnswerRepository) = CommandLineRunner {
        val author = authorRepository.save(Author(name = "Roman Fiodorov", email = "Email@example.com", roles = Role.GUEST, facebookId = "10212302171887636"))
        questionRepository.save(Question(title = "Title", content = "Content", createdBy = author))
        questionRepository.save(Question(title = "Корейский вопрос", content = "Интернетовская шутка: в Сети появился корейский вирус. Каково его действие?", createdBy = author))
        val question = questionRepository.save(Question(title = "Эйнштейнов вопрос", content = "Многие лекарственные растения имеют несколько названий, полученных ими за какие-то явные или мнимые свойства. Например, валериана иначе именуется кошачьим корнем за пристрастие к нему кошек. А от какой вирусной болезни якобы излечивал лишайник Peltigera canina — пельтигера собачья?", createdBy = author))

        val answer = Answer(content="Гладиолус?", questionId = 3, createdBy = author)
        val answer2 = Answer(content="Земляника", questionId =3, createdBy = author )
        answerRepository.save(answer)
        answerRepository.save(answer2)
        question.answers.add(answer)
        question.answers.add(answer2)
        questionRepository.save(question)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(HzApplication::class.java, *args)
}
