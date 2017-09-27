package md.fiodorov.controller

import md.fiodorov.entity.Question
import md.fiodorov.service.QuestionService
import md.fiodorov.view.CreateQuestionView
import md.fiodorov.view.ShowQuestionView
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * @author rfiodorov
 * on 19/09/17.
 */
@RestController
@RequestMapping("/questions")
class QuestionController(val questionService: QuestionService) {

    val logger: Logger = LoggerFactory.getLogger(QuestionController::class.java)

    @GetMapping
    @ResponseBody fun findAll(@RequestParam(required = false) filter: String?,
                @PageableDefault(direction = Sort.Direction.DESC, sort = arrayOf("createdDate"), size = 30)
                pageRequest: Pageable):Iterable<Question>{
        logger.debug("Called QuestionController#finadAll() with \n filter:\t{} \n pagerRequest:\t{}", filter, pageRequest)
        return questionService.findByFilter(filterString = filter, pageRequest = pageRequest)
    }

    @GetMapping(value = "/{id}", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    @ResponseBody fun findById(@PathVariable id: Long):ShowQuestionView? {
        logger.debug("Called QuestionController#findById({})",id)
        return questionService.findById(id)
    }

    @PostMapping
    fun addQuestion(@RequestBody @Valid view: CreateQuestionView){
        questionService.save(view)
    }
}
