package core.usecases

import br.group.twenty.challenge.core.entities.customer.CreateCustomer
import br.group.twenty.challenge.core.entities.customer.Customer
import br.group.twenty.challenge.core.exceptions.ResourceBusinessException
import br.group.twenty.challenge.core.exceptions.ResourceNotFoundException
import br.group.twenty.challenge.core.gateways.ICustomerGateway
import br.group.twenty.challenge.core.usecases.customer.CreateCustomerUseCase
import br.group.twenty.challenge.core.usecases.customer.GetCustomerUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetCustomerUseCaseTest {
    private val gateway: ICustomerGateway = mockk()
    private val getCustomerUseCase: GetCustomerUseCase = GetCustomerUseCase(gateway)

    @Test
    fun `should get customer successfully`() {
        val expectedCustomer = Customer(1, "lucas", "lucas@gmail.com", "42987589824")

        every { gateway.findCustomerByCpf("42987589824") } returns expectedCustomer

        val result = getCustomerUseCase.execute("42987589824")

        assertEquals(expectedCustomer, result)
        verify(exactly = 1) { gateway.findCustomerByCpf("42987589824") }
    }

    @Test
    fun `should throw ResourceBusinessException when customer already exists`() {
        every { gateway.findCustomerByCpf("42987589825") } returns null

        val exception = assertThrows<ResourceNotFoundException> {
            getCustomerUseCase.execute("42987589825")
        }

        assertEquals("Customer not found", exception.message)
        verify(exactly = 1) { gateway.findCustomerByCpf("42987589825") }
    }
}

