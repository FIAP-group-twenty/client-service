package core.usecases

import br.group.twenty.challenge.core.entities.customer.CreateCustomer
import br.group.twenty.challenge.core.entities.customer.Customer
import br.group.twenty.challenge.core.exceptions.ResourceBusinessException
import br.group.twenty.challenge.core.gateways.ICustomerGateway
import br.group.twenty.challenge.core.usecases.customer.CreateCustomerUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CreateCustomerUseCaseTest {
    private val gateway: ICustomerGateway = mockk()
    private val createCustomerUseCase: CreateCustomerUseCase = CreateCustomerUseCase(gateway)

    @Test
    fun `should create customer successfully`() {
        val createCustomerModel = CreateCustomer("lucas", "lucas@gmail.com", "42987589824")
        val expectedCustomer = Customer(1, "lucas", "lucas@gmail.com", "42987589824")

        every { gateway.findCustomerByCpf("42987589824") } returns null
        every { gateway.createCustomer(createCustomerModel) } returns expectedCustomer

        val result = createCustomerUseCase.execute(createCustomerModel)

        assertEquals(expectedCustomer, result)
        verify(exactly = 1) { gateway.findCustomerByCpf("42987589824") }
        verify(exactly = 1) { gateway.createCustomer(createCustomerModel) }
    }

    @Test
    fun `should throw ResourceBusinessException when customer already exists`() {
        val createCustomerModel = CreateCustomer("lucas", "lucas@gmail.com", "42987589824")
        val existingCustomer = Customer(1, "lucas", "lucas@gmail.com", "42987589824")

        every { gateway.findCustomerByCpf("42987589824") } returns existingCustomer

        val exception = assertThrows<ResourceBusinessException> {
            createCustomerUseCase.execute(createCustomerModel)
        }

        assertEquals("customer already exists", exception.message)
        verify(exactly = 1) { gateway.findCustomerByCpf("42987589824") }
        verify(exactly = 0) { gateway.createCustomer(any()) }
    }
}

