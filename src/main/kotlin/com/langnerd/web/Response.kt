package com.langnerd.web

import com.langnerd.model.App
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

sealed interface Response

@Serializable
data class ErrorResponse(val message: String) : Response

sealed interface SuccessResponse<T> : Response {
    val result: T
}

@Serializable
data class GetAppsResponse(override val result: List<App>) : SuccessResponse<List<App>>

@Serializable
data class GetAppDetailResponse(override val result: JsonObject) : SuccessResponse<JsonObject>
