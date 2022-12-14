package com.langnerd.model.internal

@JvmInline
value class Query(val value: String) {
    companion object {
        private val regex = Regex("[^A-Za-z\\d ]")
        private const val SafeReplacement = ""
        private const val MinLength = 3
        fun from(input: String?): Query? {
            return input?.let {
                val query = regex.replace(it, SafeReplacement).trim()
                if (query.length >= MinLength) Query(query) else null
            }
        }
    }
}
