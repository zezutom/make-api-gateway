package com.langnerd.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.langnerd.api.model.PageNumber
import com.langnerd.api.model.Query
import com.langnerd.model.App
import com.langnerd.util.FileUtil.resourceFileAsString

class AppServiceImpl(private val mapper: ObjectMapper, private val pageSize: Int = 10) : AppService {
    private val apps = loadApps()

    override suspend fun listApps(pageNumber: PageNumber?, query: Query?): Result<List<App>> {
        val filteredApps = query?.value?.let {
            apps.filter { app -> app.name.contains(it, ignoreCase = true) || app.label.contains(it, ignoreCase = true) }
        } ?: apps
        val pageIndex = pageNumber?.value ?: PageNumber.Min
        return Result.success(
            filteredApps
                .drop((pageIndex - 1) * pageSize)
                .take(pageSize)
                .sortedBy { it.name }
        )
    }

    private fun loadApps(): List<App> {
        return resourceFileAsString("make-apps.json").map { json ->
            mapper.readValue<List<App>>(json)
        }.getOrThrow()
    }
}