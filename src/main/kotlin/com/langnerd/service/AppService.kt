package com.langnerd.service

import com.langnerd.model.App
import com.langnerd.model.AppDetail
import com.langnerd.model.internal.AppName
import com.langnerd.model.internal.PageNumber
import com.langnerd.model.internal.Query

interface AppService {

    suspend fun listApps(pageNumber: PageNumber?, query: Query?): Result<List<App>>

    suspend fun getAppDetail(appName: AppName): Result<AppDetail>
}