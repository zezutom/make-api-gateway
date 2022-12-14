package com.langnerd.web.handler

import com.langnerd.model.internal.AppName
import com.langnerd.service.AppService
import com.langnerd.web.ErrorResponse
import com.langnerd.web.GetAppDetailRequest
import com.langnerd.web.GetAppDetailResponse
import com.langnerd.web.Response

class GetAppDetailHandler(private val appService: AppService) : Handler<GetAppDetailRequest>() {

    override suspend fun process(req: GetAppDetailRequest): Response {
        return req.appName?.let { appName ->
            appService.getAppDetail(AppName(appName)).map { GetAppDetailResponse(it.json) }.getOrThrow()
        } ?: ErrorResponse("Please pass application name.")
    }
}