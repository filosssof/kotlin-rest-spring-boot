package md.fiodorov.validation

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass


/**
 * @author rfiodorov
 * on 26/09/17.
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = arrayOf(NotNullOrNegativeValidator::class))
annotation class NotNullOrNegative(
        val message: String = "The value should be not null and greater than 0",
        val groups: Array<KClass<*>> = arrayOf(),
        val payload: Array<KClass<out Payload>> = arrayOf()
)


class NotNullOrNegativeValidator : ConstraintValidator<NotNullOrNegative, Long> {

    private val logger: Logger = LoggerFactory.getLogger(NotNullOrNegativeValidator::class.java)

    override fun isValid(value: Long?, context: ConstraintValidatorContext?): Boolean {
        logger.debug("NotNullOrNegativeValidator#isValid({})", value)
        return value != null && value > 0
    }

    override fun initialize(constraintAnnotation: NotNullOrNegative?) {

    }
}
