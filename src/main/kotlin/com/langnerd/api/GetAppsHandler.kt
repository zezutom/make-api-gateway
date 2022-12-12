package com.langnerd.api

import com.langnerd.api.model.*
import com.langnerd.service.AppService

class GetAppsHandler(private val appService: AppService) : Handler<GetAppsRequest>() {
    override suspend fun process(req: GetAppsRequest): Response {
        return appService.listApps(
            PageNumber.from(req.pageNumber),
            Query.from(req.query)
        ).map {
            GetAppsResponse(it)
        }.getOrThrow()
    }
}