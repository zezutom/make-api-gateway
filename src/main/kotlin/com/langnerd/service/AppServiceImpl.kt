package com.langnerd.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.langnerd.model.App
import com.langnerd.model.AppDetail
import com.langnerd.model.internal.AppName
import com.langnerd.model.internal.AuthToken
import com.langnerd.model.internal.PageNumber
import com.langnerd.model.internal.Query
import com.langnerd.model.makeapi.GetAppResponse
import com.langnerd.util.FileUtil.resourceFileAsString
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonObject

class AppServiceImpl(
    private val mapper: ObjectMapper,
    private val client: HttpClient,
    private val authToken: AuthToken,
    private val pageSize: Int = 10
) :
    AppService {
    private val apps = loadApps()

    override suspend fun listApps(pageNumber: PageNumber?, query: Query?): Result<List<App>> {
        return Result.success(apps.filter(query).paginate(pageNumber).sortedBy { it.name })
    }

    override suspend fun getAppDetail(appName: AppName): Result<AppDetail> {
        val res = client.get("https://eu1.make.com/api/v2/imt/apps/${appName.value}") {
            headers {
                append(HttpHeaders.Authorization, "Token ${authToken.value}")
                append("x-imt-apps-sdk-version", "1.3.9")
            }
            contentType(ContentType.Application.Json)
        }

        return when (res.status) {
            HttpStatusCode.OK -> {
                val json: JsonObject = res.body()
                Result.success(AppDetail(json))
            }

            else -> Result.failure(IllegalStateException("API request failed"))
        }
    }

    private fun List<App>.filter(query: Query?): List<App> {
        return query?.value?.let { value ->
            apps.filter { app ->
                app.name?.contains(value, ignoreCase = true) ?: false || app.label?.contains(
                    value,
                    ignoreCase = true
                ) ?: false
            }
        } ?: apps
    }

    private fun List<App>.paginate(pageNumber: PageNumber?): List<App> {
        return pageNumber?.value?.let { index ->
            apps.drop((index - 1) * pageSize).take(pageSize)
        } ?: apps
    }

    private fun loadApps(): List<App> {
        return resourceFileAsString("make-apps.json").map { json ->
            mapper
                .readValue<List<App>>(json)
                .toSet().toList()
                .map { app ->
                    app.icon?.let { icon ->
                        app.copy(
                            icon = "https://eu1.make.com/static/img/packages/${
                                icon.replace(
                                    ".png",
                                    "_64.png"
                                )
                            }"
                        )
                    } ?: app
                }
        }.getOrThrow()
    }
}