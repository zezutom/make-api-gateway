package com.langnerd.plugins.server

import com.langnerd.web.handler.GetAppsHandler
import com.langnerd.web.handler.GetAppDetailHandler
import com.langnerd.web.GetAppDetailRequest
import com.langnerd.web.GetAppsRequest
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting(getAppsHandler: GetAppsHandler, getAppDetailHandler: GetAppDetailHandler) {
    routing {
        static("/") {
            staticRootFolder = File("assets")
            files(".")
            default("index.html")
        }
        get("/app/{name}") {
            call.respond(
                getAppDetailHandler.handle(
                    GetAppDetailRequest(
                        call.parameters["name"]
                    )
                )
            )
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



