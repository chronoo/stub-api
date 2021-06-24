package com.example.demo.jms

import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component

@Component
class MQ1Listener {
    @JmsListener(destination = "Q1")
    fun receive(mess: Any) {
        println(mess)
    }
}

