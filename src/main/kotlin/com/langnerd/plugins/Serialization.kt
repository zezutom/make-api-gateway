package com.langnerd.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

object Serialization {

    fun Application.configureSerialization() {
        install(ContentNegotiation) {
            json()
        }
    }
}