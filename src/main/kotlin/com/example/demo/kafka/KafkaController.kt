package com.example.demo.kafka

import com.example.demo.core.Measured
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("kafka")
class KafkaController(
    var template: KafkaTemplate<String, String>
) {
    @Measured
    @GetMapping
    fun get() {
        template.send("topp", "123")
    }
}
