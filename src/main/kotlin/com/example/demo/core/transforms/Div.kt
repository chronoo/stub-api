package com.example.demo.core.transforms

import com.example.demo.core.config.ServiceMethod
import com.example.demo.core.config.ServicesPropertiesConfig

interface IExec {
    fun exec()
}

class Div(val data: ServiceMethod) : IExec {
    override fun exec() = println(42)
}
