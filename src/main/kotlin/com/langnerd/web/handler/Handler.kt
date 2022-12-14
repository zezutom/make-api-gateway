package com.langnerd.web.handler

import com.langnerd.web.ErrorResponse
import com.langnerd.web.Request
import com.langnerd.web.Response
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