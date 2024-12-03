package core.entities.mapper

import br.group.twenty.challenge.core.entities.mapper.CustomerMapper.toDto
import br.group.twenty.challenge.infrastructure.persistence.entities.CustomerEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CustomerMapperTest {

    @Test
    fun `should map customerEntity to customer sucessfully`() {
        val customerEntity = CustomerEntity(
            idCustomer = 1,
            name = "John",
            email = "john@example.com",
            cpf = "42875898545"
        )

        val customer = customerEntity.toDto()

        assertEquals(customerEntity.idCustomer, customer.id)
        assertEquals(customerEntity.name, customer.name)
        assertEquals(customerEntity.email, customer.email)
        assertEquals(customerEntity.cpf, customer.cpf)
    }
}