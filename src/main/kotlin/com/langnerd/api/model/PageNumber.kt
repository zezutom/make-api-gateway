package com.langnerd.api.model

@JvmInline
value class PageNumber(val value: Int) {
    companion object {
        const val Min = 1
        const val Max = Int.MAX_VALUE
        fun from(input: String?): PageNumber? {
            return input?.let {
                try {
                    val index = it.toInt()
                    if (index < Min || index > Max) null
                    else PageNumber(index)
                } catch (e: NumberFormatException) {
                    return null
                }
            }
        }
    }
}
