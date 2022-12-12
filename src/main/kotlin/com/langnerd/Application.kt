package com.langnerd

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.langnerd.api.GetAppsHandler
import com.langnerd.plugins.Serialization.configureSerialization
import com.langnerd.plugins.configureRouting
import com.langnerd.service.AppServiceImpl
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    // App config
    val mapper = jacksonObjectMapper()
    mapper.registerKotlinModule()
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    val appService = AppServiceImpl(mapper)

    // App server
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting(GetAppsHandler(appService))
        configureSerialization()
    }.start(wait = true)
}

