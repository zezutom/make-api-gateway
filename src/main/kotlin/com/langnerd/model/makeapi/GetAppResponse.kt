package com.langnerd.model.makeapi

import com.langnerd.model.AppDetail
import kotlinx.serialization.Serializable

@Serializable
data class GetAppResponse(val app: AppDetail)
