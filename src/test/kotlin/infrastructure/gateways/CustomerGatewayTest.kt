package infrastructure.gateways

import br.group.twenty.challenge.core.entities.customer.CreateCustomer
import br.group.twenty.challenge.core.entities.customer.Customer
import br.group.twenty.challenge.infrastructure.exceptions.ResourceInternalServerException
import br.group.twenty.challenge.infrastructure.gateways.customer.CustomerGateway
import br.group.twenty.challenge.infrastructure.persistence.entities.CustomerEntity
import br.group.twenty.challenge.infrastructure.persistence.jpa.ICustomerDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CustomerGatewayTest {
    private val dataSource: ICustomerDataSource = mockk()
    private val customerGateway: CustomerGateway = CustomerGateway(dataSource)

    private val createCustomerModel = CreateCustomer(
        name = "john",
        email = "john@gmail.com",
        cpf = "42987895821"
    )

    private val customerEntity = CustomerEntity(
        idCustomer = 1,
        name = createCustomerModel.name,
        email = createCustomerModel.email,
        cpf = createCustomerModel.cpf
    )

    private val customerDto = Customer(
        id = customerEntity.idCustomer!!,
        name = customerEntity.name,
        email = customerEntity.email,
        cpf = customerEntity.cpf
    )

    @Test
    fun `createCustomer should save customer and return created customer`() {
        every { dataSource.save(any<CustomerEntity>()) } returns customerEntity

        val result = customerGateway.createCustomer(createCustomerModel)

        assertEquals(customerDto, result)

        verify(exactly = 1) {
            dataSource.save(match {
                it.name == createCustomerModel.name &&
                        it.email == createCustomerModel.email &&
                        it.cpf == createCustomerModel.cpf
            })
        }
    }

    @Test
    fun `createCustomer should throw ResourceInternalServerException when dataSource fails`() {
        every { dataSource.save(any<CustomerEntity>()) } throws RuntimeException("Database error")

        val exception = assertThrows<ResourceInternalServerException> {
            customerGateway.createCustomer(createCustomerModel)
        }

        assertEquals("Failed to create customer john.", exception.message)

        verify(exactly = 1) {
            dataSource.save(match {
                it.name == createCustomerModel.name &&
                        it.email == createCustomerModel.email &&
                        it.cpf == createCustomerModel.cpf
            })
        }
    }

    @Test
    fun `findCustomerByCpf should return customer when cpf exists`() {
        every { dataSource.findByCpf("42987895821") } returns customerEntity

        val result = customerGateway.findCustomerByCpf("42987895821")

        assertEquals(customerDto, result)

        verify(exactly = 1) { dataSource.findByCpf("42987895821") }
    }

    @Test
    fun `findCustomerByCpf should return null when customer is not found`() {
        every { dataSource.findByCpf("42987895821") } returns null

        val result = customerGateway.findCustomerByCpf("42987895821")

        assertNull(result)

        verify(exactly = 1) { dataSource.findByCpf("42987895821") }
    }

    @Test
    fun `findCustomerByCpf should throw ResourceInternalServerException when dataSource fails`() {
        every { dataSource.findByCpf("42987895821") } throws RuntimeException("Database error")

        val exception = assertThrows<ResourceInternalServerException> {
            customerGateway.findCustomerByCpf("42987895821")
        }

        assertEquals("Failed to find customer 42987895821.", exception.message)

        verify(exactly = 1) { dataSource.findByCpf("42987895821") }
    }

}