package md.fiodorov

import md.fiodorov.entity.Question
import md.fiodorov.repository.QuestionRepository
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * @author rfiodorov
 * on 22/08/17.
 */
@SpringBootApplication
class HzApplication{
    fun init(questionRepository: QuestionRepository){
        questionRepository.save(Question(title="", content=""))
    }
}

fun main(args: Array<String>){
    SpringApplication.run(HzApplication::class.java, *args)
}
