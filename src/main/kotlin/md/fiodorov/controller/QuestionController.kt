package md.fiodorov.controller

import md.fiodorov.filter.QuestionFilter
import md.fiodorov.service.QuestionService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

/**
 * @author rfiodorov
 * on 19/09/17.
 */
@RestController
@RequestMapping("/questions")
class QuestionController(val questionService: QuestionService) {


    @GetMapping
    @ResponseBody fun findAll(@RequestParam filter: QuestionFilter,
                @PageableDefault(direction = Sort.Direction.DESC, sort = arrayOf("createdDate"), size = 30)
                pageRequest: Pageable) = questionService.findByFilter(filter = filter, pageRequest = pageRequest)

    @GetMapping(value = "/{id}", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    @ResponseBody fun findById(@PathVariable id: Long) = questionService.findById(id)

//    @PostMapping
//    fun addBook(@RequestBody view: CreateBookView){
////        val domain = view.toBookDomain().apply { author }
//        questionRepository.save(domain)
//    }
}
