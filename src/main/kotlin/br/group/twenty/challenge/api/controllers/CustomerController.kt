package br.group.twenty.challenge.api.controllers

import br.group.twenty.challenge.core.entities.customer.CreateCustomer
import br.group.twenty.challenge.core.entities.customer.Customer
import br.group.twenty.challenge.core.usecases.customer.CreateCustomerUseCase
import br.group.twenty.challenge.core.usecases.customer.GetCustomerUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/v1/customers")
@RestController
class CustomerController(
    private val createCustomerUseCase: CreateCustomerUseCase,
    private val getCustomerUseCase: GetCustomerUseCase
) {

    @PostMapping
    fun create(@RequestBody createCustomer: CreateCustomer): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(createCustomerUseCase.execute(createCustomer))
    }

    @GetMapping("/{cpf}")
    fun getCustomerByCpf(@PathVariable cpf: String): ResponseEntity<Any> {
        return ResponseEntity.ok(getCustomerUseCase.execute(cpf))
    }
}