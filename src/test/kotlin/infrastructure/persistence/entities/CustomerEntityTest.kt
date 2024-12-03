package infrastructure.persistence.entities

import br.group.twenty.challenge.infrastructure.exceptions.ResourceInternalServerException
import br.group.twenty.challenge.infrastructure.persistence.entities.CustomerEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CustomerEntityTest {

    @Test
    fun `should create CustomerEntity when all fields are valid`() {
        val customer = CustomerEntity(
            name = "John Doe",
            email = "john.doe@example.com",
            cpf = "42987895821"
        )

        assertNotNull(customer)
        assertEquals("John Doe", customer.name)
        assertEquals("john.doe@example.com", customer.email)
        assertEquals("42987895821", customer.cpf)
    }

    @Test
    fun `should throw ResourceInternalServerException when email is invalid`() {
        val invalidEmail = "invalid-email"

        val exception = assertThrows<ResourceInternalServerException> {
            CustomerEntity(
                name = "John Doe",
                email = invalidEmail,
                cpf = "42987895821"
            )
        }

        assertEquals("Invalid email", exception.message)
    }

    @Test
    fun `should throw ResourceInternalServerException when cpf is invalid`() {
        val invalidCpf = "12345"

        val exception = assertThrows<ResourceInternalServerException> {
            CustomerEntity(
                name = "John Doe",
                email = "john.doe@example.com",
                cpf = invalidCpf
            )
        }

        assertEquals("Invalid cpf", exception.message)
    }

    @Test
    fun `should throw ResourceInternalServerException when name is blank`() {
        val blankName = ""

        val exception = assertThrows<ResourceInternalServerException> {
            CustomerEntity(
                name = blankName,
                email = "john.doe@example.com",
                cpf = "42987895821"
            )
        }

        assertEquals("Name cannot be empty", exception.message)
    }
}