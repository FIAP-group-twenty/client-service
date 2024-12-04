package core.entities.customer

import br.group.twenty.challenge.core.entities.customer.CreateCustomer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CreateCustomerTest {

    private val objectMapper = jacksonObjectMapper()

    @Test
    fun `should create a valid instance of CreateCustomer`() {
        val name = "John Doe"
        val email = "john.doe@example.com"
        val cpf = "12345678901"

        val customer = CreateCustomer(name = name, email = email, cpf = cpf)

        assertEquals(name, customer.name)
        assertEquals(email, customer.email)
        assertEquals(cpf, customer.cpf)
    }

    @Test
    fun `should correctly serialize and deserialize CreateCustomer`() {
        val customer = CreateCustomer(name = "Jane Doe", email = "jane.doe@example.com", cpf = "98765432100")
        val json = """{"name":"Jane Doe","email":"jane.doe@example.com","cpf":"98765432100"}"""

        val serialized = objectMapper.writeValueAsString(customer)
        val deserialized = objectMapper.readValue<CreateCustomer>(json)

        assertEquals(json, serialized)
        assertEquals(customer, deserialized)
    }
}