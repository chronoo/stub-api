package com.example.demo.kafka

import com.example.demo.core.Measured
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@EnableKafka
@Component
class KafkaListener {
    @Measured
    @KafkaListener(topics = ["topp"], groupId = "foo")
    fun read(msg: String) {
        println(msg)
    }
}
