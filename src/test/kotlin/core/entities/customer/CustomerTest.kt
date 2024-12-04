package core.entities.customer

import br.group.twenty.challenge.core.entities.customer.Customer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class CustomerTest {

    private val objectMapper = jacksonObjectMapper()

    @Test
    fun `should create a valid instance of Customer`() {
        val id = 1
        val name = "Alice Smith"
        val email = "alice.smith@example.com"
        val cpf = "12345678901"

        val customer = Customer(id = id, name = name, email = email, cpf = cpf)

        assertEquals(id, customer.id)
        assertEquals(name, customer.name)
        assertEquals(email, customer.email)
        assertEquals(cpf, customer.cpf)
    }

    @Test
    fun `should create a Customer with a null id`() {
        val name = "Bob Johnson"
        val email = "bob.johnson@example.com"
        val cpf = "98765432100"

        val customer = Customer(name = name, email = email, cpf = cpf)

        assertNull(customer.id)
        assertEquals(name, customer.name)
        assertEquals(email, customer.email)
        assertEquals(cpf, customer.cpf)
    }

    @Test
    fun `should correctly compare two instances of Customer`() {
        val customer1 = Customer(id = 1, name = "Charlie Brown", email = "charlie.brown@example.com", cpf = "11122233344")
        val customer2 = Customer(id = 1, name = "Charlie Brown", email = "charlie.brown@example.com", cpf = "11122233344")

        assertEquals(customer1, customer2)
        assertEquals(customer1.hashCode(), customer2.hashCode())
    }

    @Test
    fun `should correctly serialize and deserialize a Customer`() {
        val customer = Customer(id = 2, name = "Diana Prince", email = "diana.prince@example.com", cpf = "55566677788")
        val json = """{"id":2,"name":"Diana Prince","email":"diana.prince@example.com","cpf":"55566677788"}"""

        val serialized = objectMapper.writeValueAsString(customer)
        val deserialized = objectMapper.readValue<Customer>(json)

        assertEquals(json, serialized)
        assertEquals(customer, deserialized)
    }

    @Test
    fun `should handle missing fields during deserialization`() {
        val json = """{"name":"Eve Adams","email":"eve.adams@example.com","cpf":"99988877766"}"""

        val customer = objectMapper.readValue<Customer>(json)

        assertNull(customer.id)
        assertEquals("Eve Adams", customer.name)
        assertEquals("eve.adams@example.com", customer.email)
        assertEquals("99988877766", customer.cpf)
    }
}