package com.example.demo

import com.example.demo.core.ConfigurableServicesController
import com.example.demo.core.ConfigurableServicesHandler
import org.apache.activemq.ActiveMQConnectionFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.annotation.JmsListener
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import org.springframework.jms.config.SimpleJmsListenerEndpoint
import org.springframework.stereotype.Component


@ConfigurationPropertiesScan
@SpringBootApplication
class StubApplication {
    @Bean
    fun myCustomHandler(configurableServicesController: ConfigurableServicesController) =
        ConfigurableServicesHandler(configurableServicesController).apply { order = 1 }
}

@Configuration
@EnableJms
class ReceiverConfig {
    val brokerUrl = ""
    val status1Destination = ""
    val status2Destination = ""

    @Bean
    fun receiverActiveMQConnectionFactory() =
        ActiveMQConnectionFactory().apply { brokerURL = brokerUrl }

    @Bean
    fun orderDefaultJmsListenerContainerFactory() =
        DefaultJmsListenerContainerFactory().apply {
            setConnectionFactory(receiverActiveMQConnectionFactory())
            setConcurrency("3-10")
        }

    @Bean
    fun orderMessageListenerContainer() =
        SimpleJmsListenerEndpoint()
}

fun main(args: Array<String>) {
    runApplication<StubApplication>(*args)
}

@Component
class Receiver {
    @JmsListener(destination = "qeue1", containerFactory = "factory1")
    fun receive(obj: Any) {
        println(obj)
    }
}
