package com.langnerd.util

object FileUtil {

    fun resourceFileAsString(filename: String): Result<String> {
        return try {
            FileUtil.javaClass.classLoader.getResource(filename)?.readText(Charsets.UTF_8)
                ?.let { Result.success(it) } ?: Result.failure(IllegalArgumentException())
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}