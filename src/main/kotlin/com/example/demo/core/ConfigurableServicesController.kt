package com.example.demo.core

import com.example.demo.core.config.ServicesPropertiesConfig
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.AbstractController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ConfigurableServicesController(private val prop: ServicesPropertiesConfig) : AbstractController() {
    @Throws(java.lang.Exception::class)
    override fun handleRequestInternal(request: HttpServletRequest,
                                       response: HttpServletResponse): ModelAndView? =
        measure("${request.method} ${request.requestURI}") {
            try {
                val requestUri = request.requestURI.removePrefix("/")
                prop.endpoints.firstOrNull { endpoint -> endpoint.method?.equals(request.method) ?: true && endpoint.url?.startsWith(requestUri) ?: false }
                    ?.let {
                        Thread.sleep(it.delay ?: 0)
                        response.contentType = it.contentType ?: MediaType.APPLICATION_JSON_VALUE
                        response.writer.println(it.jsonPath)
                    } ?: throw NotFoundException("Method not found")
            } catch (e: Exception) {
                logger.error("Handle request error", e)
                throw e
            }
            null
        }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)
