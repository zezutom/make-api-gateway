package com.langnerd.api

import com.langnerd.api.model.ErrorResponse
import com.langnerd.api.model.Request
import com.langnerd.api.model.Response
import org.slf4j.LoggerFactory

abstract class Handler<Req : Request> {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    companion object {
        const val ClientErrorMessage = "Oops, something went wrong!"
    }

    protected abstract suspend fun process(req: Req): Response

    suspend fun handle(req: Req): Response {
        return try {
            process(req)
        } catch (t: Throwable) {
            logger.error("Failed to handle request", t)
            return ErrorResponse(ClientErrorMessage)
        }
    }
}