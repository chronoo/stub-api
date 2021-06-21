package com.example.demo.core

import org.springframework.web.servlet.handler.AbstractHandlerMapping
import javax.servlet.http.HttpServletRequest

class ConfigurableServicesHandler(
    private val configurableServicesController: ConfigurableServicesController
) : AbstractHandlerMapping() {
    @Throws(Exception::class)
    override fun getHandlerInternal(request: HttpServletRequest) = configurableServicesController
}
