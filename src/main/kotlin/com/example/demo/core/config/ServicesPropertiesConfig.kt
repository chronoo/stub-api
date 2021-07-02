package com.example.demo.core.config

import com.example.demo.core.Executable
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Component

@ConstructorBinding
@ConfigurationProperties(prefix = "config")
class ServicesPropertiesConfig(services: List<Service>) {
    val endpoints: List<Pair<ServiceMethod, String>> = services.flatMap {
        it.endpoints.map { endpoint -> endpoint to it.name }
    }
}

@Component
class Props(conf: ServicesPropertiesConfig) {
    val aaa: List<Executable> = conf.endpoints.map {
        it.let {
            val clazz: Class<Executable> = javaClass.classLoader.loadClass(it.first.prototype) as Class<Executable>
            val executor: Executable = clazz.getDeclaredConstructor().newInstance() as Executable
            executor.execute()
            executor
        }
    }
}
