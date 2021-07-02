package com.example.demo.services

import com.example.demo.core.Aaaaa
import com.example.demo.core.Measured
import org.springframework.jms.core.JmsOperations
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController(
    val jmsOperations: JmsOperations,
    val a: Aaaaa
) {
    @GetMapping("/jms/{queue}")
    @Measured
    fun helloWorld(@PathVariable queue: String): Resp {
        jmsOperations.convertAndSend(queue, 123)
        return Resp()
    }

    @GetMapping("/jmss")
    @Measured
    fun helloWorld1() {
        a.doSomething("plus")
    }
}

data class Resp(
    val success: Boolean = false
)
