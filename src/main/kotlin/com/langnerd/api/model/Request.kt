package com.langnerd.api.model

import kotlinx.serialization.Serializable

sealed interface Request

@Serializable
data class GetAppsRequest(val pageNumber: String?, val query: String?) : Request