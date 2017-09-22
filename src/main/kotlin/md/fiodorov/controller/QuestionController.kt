package md.fiodorov.controller

import md.fiodorov.repository.QuestionRepository
import md.fiodorov.service.QuestionService
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
class QuestionController (val questionService: QuestionService) {

//    @GetMapping
//    fun findAll() = questionRepository.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) = questionService.findById(id)

//    @PostMapping
//    fun addBook(@RequestBody view: CreateBookView){
////        val domain = view.toBookDomain().apply { author }
//        questionRepository.save(domain)
//    }
}
