package com.example.demo.core.config

data class ServiceMethod(
    val transform: String?,
    val url: String,
    val contentType: String,
    val jsonPath: String?,
    val delay: Long?,
    val prototype: String?
)
