package com.example.demo

import com.example.demo.core.ConfigurableServicesController
import com.example.demo.core.ConfigurableServicesHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


@ConfigurationPropertiesScan
@SpringBootApplication
class StubApplication {
    @Bean
    fun myCustomHandler(configurableServicesController: ConfigurableServicesController) =
        ConfigurableServicesHandler(configurableServicesController).apply { order = 1 }
}

fun main(args: Array<String>) {
    runApplication<StubApplication>(*args)
}

