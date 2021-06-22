package com.example.demo.core.config

data class ServiceMethod(
    val url: String,
    val contentType: String,
    val jsonPath: String?,
    val delay: Long?
)
