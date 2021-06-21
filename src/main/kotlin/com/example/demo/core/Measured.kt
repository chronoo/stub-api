package com.example.demo.core

import com.example.demo.StubApplication
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class MeasureAspect {
    @Around("@annotation(Measured)")
    fun measureMethod(point: ProceedingJoinPoint) = measure(point.signature.name) { point.proceed() }
}

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Measured

val logger: Logger = LoggerFactory.getLogger(StubApplication::class.java)

fun <T> measure(methodName: String? = null, action: () -> T): T? =
    with(System.currentTimeMillis()) {
        try {
            action.invoke()
        } catch (e: Exception) {
            throw e
        } finally {
            logger.info("$methodName - ${System.currentTimeMillis() - this} ms")
        }
    }
