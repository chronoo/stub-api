package com.example.demo.core.config

class Service(
    name: String,
    val endpoints: List<ServiceMethod>?,
    url: String?,
    jsonPath: String?,
    contentType: String?,
    delay: Long?
) : Endpoint(url, jsonPath, contentType, delay, null)
