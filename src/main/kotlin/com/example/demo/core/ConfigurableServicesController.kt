package com.example.demo.core

import com.example.demo.core.config.ServiceMethod
import com.example.demo.core.config.ServicesPropertiesConfig
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import org.springframework.web.servlet.mvc.AbstractController
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class ConfigurableServicesController(
    private val prop: ServicesPropertiesConfig,
    private val executors: Map<String, Executable>
) : AbstractController() {
    @Throws(java.lang.Exception::class)
    override fun handleRequestInternal(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ModelAndView? =
        measure("${request.method} ${request.requestURI}") {
            prop.endpoints
                .map { it.first }
                .firstOrNull { endpoint -> endpoint.url.startsWith(request.requestURI) }
                ?.let {
                    Thread.sleep(it.delay ?: 0)
                    response.contentType = it.contentType
                    response.body = it.jsonPath
                } ?: throw NotFoundException()
            null
        }
}

@Component
class TransformationsInterceptor(
    private val prop: ServicesPropertiesConfig,
    private val executors: Map<String, Executable>
) : HandlerInterceptorAdapter() {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        return super.preHandle(request, response, handler)
    }

    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
        super.postHandle(request, response, handler, modelAndView)
        if (!request.requestURI.isBlank()) {
            prop.endpoints.map { it.first }.firstOrNull { endpoint -> endpoint.url.startsWith(request.requestURI) }
                ?.let {
                    it.transform?.let {
                        transform -> executors[transform]?.execute()
                    }
                }
        }
    }
}

var ServletResponse.body: String?
    get() = writer.toString()
    set(value) = writer.println(value)

@RestController
class EndpointDelayController(val prop: ServicesPropertiesConfig) {
    @PostMapping
    fun updateDelay(endpoint: ServiceMethod) {
        prop.endpoints.replace(endpoint, { it == endpoint.url })
    }
}

fun <T> List<T>.replace(newValue: T, block: (T) -> Boolean): List<T> =
    map { if (block(it)) newValue else it }

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)

@Controller
class Test {
    @GetMapping(value = ["test"])
    @ResponseBody
    fun aaa() = "42"
}

interface Executable {
    fun execute() = println(42)
}

@Component
class Aaaaa(
    val executors: Map<String, Executable>
) {
    fun doSomething(method: String) =
        executors[method]?.execute()
}
