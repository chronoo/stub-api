package com.example.demo.core

import com.example.demo.core.config.ServiceMethod
import com.example.demo.core.config.ServicesPropertiesConfig
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.AbstractController
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class ConfigurableServicesController(private val prop: ServicesPropertiesConfig) : AbstractController() {
    @Throws(java.lang.Exception::class)
    override fun handleRequestInternal(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ModelAndView? =
        measure("${request.method} ${request.requestURI}") {
            prop.endpoints.firstOrNull { endpoint -> endpoint.url.startsWith(request.requestURI) }
                ?.let {
                    Thread.sleep(it.delay ?: 0)
                    response.contentType = it.contentType
                    response.body = it.jsonPath
                }
            null
        }
}

var ServletResponse.body: String?
    get() = writer.toString()
    set(value) = writer.println(value)

@RestController
class EndpointDelayController(val prop: ServicesPropertiesConfig) {
    @PostMapping
    fun updateDelay(endpoint: ServiceMethod) {
        prop.endpoints.replace(endpoint, { it.url == endpoint.url })
    }
}

fun <T> List<T>.replace(newValue: T, block: (T) -> Boolean): List<T> =
    map { if (block(it)) newValue else it }

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)
