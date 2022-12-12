package com.langnerd.service

import com.langnerd.model.App
import com.langnerd.api.model.PageNumber
import com.langnerd.api.model.Query

interface AppService {

    suspend fun listApps(pageNumber: PageNumber?, query: Query?): Result<List<App>>
}