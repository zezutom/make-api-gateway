package com.langnerd.model

import kotlinx.serialization.Serializable

@Serializable
data class App(
    val beta: Boolean,
    val icon: String,
    val name: String,
    val label: String,
    val theme: String,
    val public: Boolean
)
