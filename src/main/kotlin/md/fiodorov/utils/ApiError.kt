package md.fiodorov.utils

import org.springframework.http.HttpStatus
import java.time.Instant

/**
 * @author rfiodorov
 * on 28/09/17.
 */
data class ApiError(
        var status: HttpStatus,
        var timestamp: Instant = Instant.now(),
        var message: String? = "Unexpected error",
        var subErrors: Iterable<String> = listOf()) {
    constructor(status: HttpStatus, exception: Throwable) : this(status = status, message = exception.message)
}
