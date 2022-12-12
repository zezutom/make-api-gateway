package com.langnerd.plugins

import com.langnerd.api.GetAppsHandler
import com.langnerd.api.model.GetAppsRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(getAppsHandler: GetAppsHandler) {
    routing {
        get("/") {
            call.respond(HttpStatusCode.OK)
        }
        get("/apps") {
            call.respond(
                getAppsHandler.handle(
                    GetAppsRequest(
                        call.request.queryParameters["page"],
                        call.request.queryParameters["query"]
                    )
                )
            )
        }
    }
}



