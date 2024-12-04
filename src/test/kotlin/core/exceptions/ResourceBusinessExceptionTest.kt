package core.exceptions

import br.group.twenty.challenge.core.exceptions.ResourceBusinessException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ResourceBusinessExceptionTest {

    @Test
    fun `should throw ResourceBusinessException with correct message`() {
        val exception = assertThrows<ResourceBusinessException> {
            throw ResourceBusinessException("customer already exists")
        }
        assertEquals("customer already exists", exception.message)
    }

    @Test
    fun `formatter should return ErrorResponse with correct message`() {
        val message = "Test error message"
        val exception = ResourceBusinessException(message)

        val errorResponse = exception.formatter()

        assertEquals(message, errorResponse.message)
    }
}