package com.langnerd.web.handler

import com.langnerd.model.internal.PageNumber
import com.langnerd.model.internal.Query
import com.langnerd.service.AppService
import com.langnerd.web.GetAppsRequest
import com.langnerd.web.GetAppsResponse
import com.langnerd.web.Response

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