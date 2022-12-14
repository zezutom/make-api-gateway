package com.langnerd.web

import kotlinx.serialization.Serializable

sealed interface Request

@Serializable
data class GetAppsRequest(val pageNumber: String?, val query: String?) : Request

@Serializable
data class GetAppDetailRequest(val appName: String?) : Request