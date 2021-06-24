package com.example.demo.jms

import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.annotation.JmsListenerConfigurer
import org.springframework.jms.config.JmsListenerEndpointRegistrar
import org.springframework.jms.config.SimpleJmsListenerEndpoint
import org.springframework.jms.listener.adapter.MessageListenerAdapter
import javax.jms.Message
import javax.jms.Session

@Configuration
@EnableJms
class MQListeners : JmsListenerConfigurer {
    override fun configureJmsListeners(registrar: JmsListenerEndpointRegistrar) {
        registrar.registerEndpoint(
            SimpleJmsListenerEndpoint().apply {
                id = "Q3Listener"
                destination = "Q3"
                messageListener = MQListener()
            }
        )
    }
}

private class MQListener : MessageListenerAdapter() {
    override fun onMessage(message: Message, session: Session?) {
        println(message)
    }
}
