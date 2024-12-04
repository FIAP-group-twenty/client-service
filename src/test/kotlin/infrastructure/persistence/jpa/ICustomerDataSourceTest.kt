package infrastructure.persistence.jpa

import api.config.TestConfig
import br.group.twenty.challenge.infrastructure.persistence.entities.CustomerEntity
import br.group.twenty.challenge.infrastructure.persistence.jpa.ICustomerDataSource
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@ContextConfiguration(classes = [TestConfig::class])
class ICustomerDataSourceTest {

    @Autowired
    private lateinit var customerDataSource: ICustomerDataSource

    @Test
    fun `should find customer by CPF`() {
        // Dado: Um cliente salvo no banco
        val customer = CustomerEntity(
            idCustomer = null, // Será gerado automaticamente
            name = "John Doe",
            cpf = "12345678900",
            email = "john.doe@example.com"
        )
        customerDataSource.save(customer)

        // Quando: Buscamos pelo CPF
        val foundCustomer = customerDataSource.findByCpf("12345678900")

        // Então: O cliente correto deve ser retornado
        assertNotNull(foundCustomer)
        assertEquals("John Doe", foundCustomer?.name)
        assertEquals("12345678900", foundCustomer?.cpf)
    }

    @Test
    fun `should return null if CPF does not exist`() {
        // Quando: Buscamos por um CPF inexistente
        val foundCustomer = customerDataSource.findByCpf("99999999999")

        // Então: O resultado deve ser nulo
        assertNull(foundCustomer)
    }
}