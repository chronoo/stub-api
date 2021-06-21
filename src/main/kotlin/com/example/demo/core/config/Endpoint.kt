package com.example.demo.core.config

abstract class Endpoint(
    val url: String?,
    val jsonPath: String?,
    val contentType: String?,
    val delay: Long?,
    val method: String?)
