package com.langnerd.plugins.server

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.langnerd.model.internal.AuthToken
import com.langnerd.plugins.client.configureContentNegotiation
import com.langnerd.plugins.client.configureLogging
import com.langnerd.plugins.client.configureRetries
import com.langnerd.plugins.server.Serialization.configureSerialization
import com.langnerd.service.AppServiceImpl
import com.langnerd.web.handler.GetAppDetailHandler
import com.langnerd.web.handler.GetAppsHandler
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.server.application.*
import java.lang.IllegalStateException

fun Application.configureApplication() {
    val authToken = AuthToken(authTokenProperty())
    val mapper = jacksonObjectMapper()
    mapper.registerKotlinModule()
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    val client = HttpClient(CIO) {
        configureContentNegotiation()
        configureLogging()
        configureRetries(3)
    }
    val appService = AppServiceImpl(mapper, client, authToken)
    configureRouting(GetAppsHandler(appService), GetAppDetailHandler(appService))
    configureSerialization()
}

private fun authTokenProperty(): String {
    val propertyName = "MAKE_AUTH_TOKEN"
    return System.getProperty(propertyName) ?: System.getenv(propertyName)
    ?: throw IllegalStateException("Missing auth token!")
}