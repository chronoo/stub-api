package com.example.demo.core.config

import com.example.demo.core.ConfigurableServicesController
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "config")
class ServicesPropertiesConfig(services: List<Service>) {
    private val logger = LoggerFactory.getLogger(ConfigurableServicesController::class.java)
    val endpoints: List<Endpoint>

    init {
        endpoints = try {
            val rootEndpoints: List<Endpoint> = services.map { service -> service }
            val nestedEndpoints: List<Endpoint> = services.flatMap { service ->
                service.endpoints?.map { endpoint -> ServiceMethod(endpoint, service) } ?: listOf()
            }
            nestedEndpoints + rootEndpoints
        } catch (e: Exception) {
            logger.error("Props init faults", e)
            listOf()
        }
    }
}
