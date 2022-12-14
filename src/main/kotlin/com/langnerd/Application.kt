package com.langnerd

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.langnerd.plugins.client.configureContentNegotiation
import com.langnerd.plugins.client.configureLogging
import com.langnerd.plugins.client.configureRetries
import com.langnerd.plugins.server.Serialization.configureSerialization
import com.langnerd.plugins.server.configureApplication
import com.langnerd.plugins.server.configureRouting
import com.langnerd.service.AppServiceImpl
import com.langnerd.web.handler.GetAppDetailHandler
import com.langnerd.web.handler.GetAppsHandler
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*

fun main() {
    // App server
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::configureApplication
    ).start(wait = true)
}

