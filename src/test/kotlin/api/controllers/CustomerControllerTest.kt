package api.controllers

import br.group.twenty.challenge.api.controllers.CustomerController
import br.group.twenty.challenge.core.entities.customer.CreateCustomer
import br.group.twenty.challenge.core.entities.customer.Customer
import br.group.twenty.challenge.core.exceptions.ResourceBusinessException
import br.group.twenty.challenge.core.gateways.ICustomerGateway
import br.group.twenty.challenge.core.usecases.customer.CreateCustomerUseCase
import br.group.twenty.challenge.core.usecases.customer.GetCustomerUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class CustomerControllerTest {
    private val createCustomerUseCase: CreateCustomerUseCase = mockk()
    private val getCustomerUseCase: GetCustomerUseCase = mockk()
    private val customerController: CustomerController = CustomerController(createCustomerUseCase, getCustomerUseCase)


    private val createCustomer = CreateCustomer(name = "John", email = "john@gmail.com", cpf = "12345678912")
    private val customer = Customer(id = 1, name = "John", email = "john@gmail.com", cpf = "12345678912")


    @Test
    fun `must create a customer`(){
        every { createCustomerUseCase.execute(any()) } returns customer
        val response = customerController.create(createCustomer)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(customer, response.body)
        verify(exactly = 1) { createCustomerUseCase.execute(any())  }
    }

    @Test
    fun `must return a customer`(){
        every { getCustomerUseCase.execute(any()) } returns customer
        val response = customerController.getCustomerByCpf("12345678912")
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(customer, response.body)
        verify(exactly = 1) { getCustomerUseCase.execute(any())  }
    }

}