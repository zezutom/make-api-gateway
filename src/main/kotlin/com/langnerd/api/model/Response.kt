package com.langnerd.api.model

import com.langnerd.model.App
import kotlinx.serialization.Serializable

sealed interface Response

@Serializable
data class ErrorResponse(val message: String) : Response

sealed interface SuccessResponse<T> : Response {
    val result: T
}

@Serializable
data class GetAppsResponse(override val result: List<App>) : SuccessResponse<List<App>>


