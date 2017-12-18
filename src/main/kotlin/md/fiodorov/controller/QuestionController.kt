package md.fiodorov.controller

import md.fiodorov.entity.Answer
import md.fiodorov.entity.Question
import md.fiodorov.service.QuestionService
import md.fiodorov.validation.NotNullOrNegative
import md.fiodorov.view.CreateUpdateAnswerView
import md.fiodorov.view.CreateUpdateQuestionView
import md.fiodorov.view.ShowQuestionView
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid


/**
 * @author rfiodorov
 * on 19/09/17.
 */
@RestController
@RequestMapping("/questions")
class QuestionController(val questionService: QuestionService) {

    val logger: Logger = LoggerFactory.getLogger(QuestionController::class.java)

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = [(MediaType.APPLICATION_JSON_VALUE)])
    fun findAll(@RequestParam(required = false) filter: String?,
                @PageableDefault(direction = Sort.Direction.DESC, sort = ["createdDate"], size = 30)
                pageRequest: Pageable): Page<Question> {
        logger.debug("Called QuestionController#finadAll() with \n filter:\t{} \n pagerRequest:\t{}", filter, pageRequest)
        return questionService.findByFilter(filterString = filter, pageRequest = pageRequest)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = ["/{id}"], produces = [(MediaType.APPLICATION_JSON_VALUE)])
    fun findById(@PathVariable id: Long): ShowQuestionView? {
        logger.debug("Called QuestionController#findById({})", id)
        return questionService.findById(id)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun addQuestion(@RequestBody @Valid view: CreateUpdateQuestionView) {
        logger.debug("Called QuestionController#addQuestion({})", view)
        questionService.add(view)
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(value = ["/{id}"])
    fun editQuestion(@PathVariable @NotNullOrNegative id: Long, @RequestBody @Valid view: CreateUpdateQuestionView) {
        logger.debug("Called QuestionController#editQuestion({})", view)
        questionService.edit(id,view)
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(value = ["/{id}"])
    fun deleteQuestion(@PathVariable @NotNullOrNegative id: Long){
        logger.debug("Called QuestionController#deleteQuestion({})", id)
        questionService.delete(id)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = ["/{id}/answers"])
    @ResponseBody
    fun getAnswersByQuestionId(@PathVariable @NotNullOrNegative id: Long,
                               @PageableDefault(direction = Sort.Direction.DESC, sort = arrayOf("createdDate"), size = 30)
                                pageRequest: Pageable, principal: Principal?): Page<Answer> {
        logger.debug("Called QuestionController#getAnswersByQuestionId({}) ", id)
        return questionService.getAnswersByQuestionId(id, pageRequest)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = ["/{id}/answers"])
//    @Throws(NotLoggedInException::class, NotEnoughKarmaException::class)
    fun addAnswer(@PathVariable id: Long?, @RequestBody answerView: CreateUpdateAnswerView,
                  principal: Principal?) {
        logger.debug("Called addAnswer for question id={} with content={} by {}",
                id, answerView.content, principal?.name)
        questionService.addAnswerForQuestion(answerView.questionId, answerView.content, principal?.name)
    }
}
