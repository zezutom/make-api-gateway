package com.langnerd.model

import kotlinx.serialization.Serializable

@Serializable
data class App(
    val name: String?,
    val icon: String?,
    val label: String?,
    val theme: String?,
    val beta: Boolean?,
    val public: Boolean?
)
