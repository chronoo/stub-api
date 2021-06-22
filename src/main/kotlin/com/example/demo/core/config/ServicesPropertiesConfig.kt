package com.example.demo.core.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "config")
class ServicesPropertiesConfig(services: List<Service>) {
    val endpoints: MutableList<ServiceMethod> = services.flatMap { it.endpoints }.toMutableList()
}
