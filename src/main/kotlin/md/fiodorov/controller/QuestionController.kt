package md.fiodorov.controller

import md.fiodorov.repository.QuestionRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author rfiodorov
 * on 19/09/17.
 */
@RestController
@RequestMapping("/questions")
class QuestionController (val questionRepository: QuestionRepository) {

    @GetMapping
    fun findAll() = questionRepository.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) = questionRepository.findOne(id)

//    @PostMapping
//    fun addBook(@RequestBody view: CreateBookView){
////        val domain = view.toBookDomain().apply { author }
//        questionRepository.save(domain)
//    }
}
