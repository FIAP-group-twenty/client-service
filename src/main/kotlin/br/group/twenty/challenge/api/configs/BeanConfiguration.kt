package br.group.twenty.challenge.api.configs

import br.group.twenty.challenge.core.usecases.customer.CreateCustomerUseCase
import br.group.twenty.challenge.core.usecases.customer.GetCustomerUseCase
import br.group.twenty.challenge.infrastructure.gateways.customer.CustomerGateway
import br.group.twenty.challenge.infrastructure.persistence.jpa.ICustomerDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfiguration(
    val customerDataSource: ICustomerDataSource,
) {

    @Bean
    fun customerGateway(): CustomerGateway {
        return CustomerGateway(customerDataSource)
    }

    @Bean
    fun createCustomerUseCase(): CreateCustomerUseCase {
        return CreateCustomerUseCase(customerGateway())
    }

    @Bean
    fun getCustomerUseCase(): GetCustomerUseCase {
        return GetCustomerUseCase(customerGateway())
    }
}