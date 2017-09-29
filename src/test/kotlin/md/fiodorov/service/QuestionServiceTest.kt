package md.fiodorov.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.nhaarman.mockito_kotlin.*
import md.fiodorov.entity.Question
import md.fiodorov.filter.QuestionFilter
import md.fiodorov.repository.AuthorRepository
import md.fiodorov.repository.QuestionRepository
import org.junit.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specifications

/**
 * @author rfiodorov
 * on 29/09/17.
 */

class QuestionServiceTest {

    private val questionRepositoryMock: QuestionRepository = mock()
    private val authorRepositoryMock: AuthorRepository = mock()
    private val toTest: QuestionService = QuestionService(questionRepositoryMock, authorRepositoryMock)

    private val defaultPageRequest = PageRequest(0, 30, Sort.Direction.DESC, "createdDate")

    private val filterString = "{\"minRank\":0}"
    private val filter: QuestionFilter = ObjectMapper().readValue(filterString)
    private val specification: Specifications<Question> = filter.toSpecification()

    @Test
    fun findByFilterNoFilterTest() {
        whenever(questionRepositoryMock.findAll(defaultPageRequest)).thenReturn(PageImpl<Question>(listOf()))
        toTest.findByFilter("", defaultPageRequest)
        verify(questionRepositoryMock, times(1)).findAll(defaultPageRequest)
        verify(questionRepositoryMock, times(0)).findAll(any<Specifications<Question>>(), any<Pageable>())
    }

    @Test
    fun findByFilterWithFilterTest() {
        whenever(questionRepositoryMock.findAll(any<Specifications<Question>>(), any<Pageable>())).thenReturn(PageImpl<Question>(listOf()))
        toTest.findByFilter(filterString, defaultPageRequest)
        verify(questionRepositoryMock, times(0)).findAll(any<Pageable>())
        verify(questionRepositoryMock, times(1)).findAll(any<Specifications<Question>>(), any<Pageable>())
    }
}
