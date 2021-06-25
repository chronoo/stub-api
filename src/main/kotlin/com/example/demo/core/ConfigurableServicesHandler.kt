package com.example.demo.core

import com.example.demo.core.config.ServicesPropertiesConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping
import java.util.*

@Configuration
class Conf(
    private val config: ServicesPropertiesConfig,
    private val configurableServicesController: ConfigurableServicesController
) {
    @Bean
    fun simpleUrlHandlerMapping() =
        SimpleUrlHandlerMapping().apply {
            order = 0
            setMappings(
                Properties().apply {
                    config.endpoints.forEach {
                        put(it.url, configurableServicesController)
                    }
                }
            )
        }
}
