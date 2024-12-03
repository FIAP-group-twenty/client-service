package core.exceptions

import br.group.twenty.challenge.core.exceptions.ResourceNotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ResourceNotFoundExceptionTest {

    @Test
    fun `should throw ResourceNotFoundException with correct message`() {
        val exception = assertThrows<ResourceNotFoundException> {
            throw ResourceNotFoundException("Customer not found")
        }
        Assertions.assertEquals("Customer not found", exception.message)
    }
}