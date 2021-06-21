package com.example.demo.core.config

import org.springframework.http.MediaType

open class ServiceMethod(
   url: String?,
   jsonPath: String?,
   contentType: String?,
   delay: Long?,
   method: String?
): Endpoint(url, jsonPath, contentType, delay, method) {
    constructor(current: ServiceMethod, parent : Service) : this(
        current.url ?: parent.url ?: throw IllegalStateException("Endpoint url not specified"),
        current.jsonPath ?: parent.jsonPath ?: throw IllegalStateException("JsonPath not specified"),
        current.contentType ?: parent.contentType ?: MediaType.APPLICATION_JSON_VALUE,
        current.delay?: parent.delay ?: 0,
        current.method ?: throw IllegalStateException("REST-Method not specified")
    )
}

