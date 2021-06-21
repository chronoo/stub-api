package com.example.demo.services

import com.example.demo.core.Measured
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {
    @GetMapping
    @Measured
    fun helloWorld(): Resp = Resp()

    @PostMapping
    @Measured
    fun helloWorld1(): Resp = Resp()
}

data class Resp(
    val success: Boolean = false
)
