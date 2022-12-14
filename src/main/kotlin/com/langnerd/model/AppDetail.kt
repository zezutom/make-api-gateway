package com.langnerd.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class AppDetail(val json: JsonObject)
