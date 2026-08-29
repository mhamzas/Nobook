package com.ycngmn.nobook.utils

import androidx.annotation.RawRes
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

const val SCRIPT_SRC = "https://raw.githubusercontent.com/ycngmn/Nobook/refs/heads/main/app/src/main/res/raw/"

data class Script(
    val isEnabled: Boolean,
    @param:RawRes val resourceId: Int,
    val scriptTitle: String
)

/**
 * Builds the script bundle from APK resources. Remote scripts are deliberately
 * opt-in so Facebook remains usable offline and app behavior stays versioned.
 */
suspend fun fetchScripts(
    scripts: List<Script>,
    fallbackContent: (Int) -> String,
    fetchRemote: Boolean = false
): String {
    val localContent = buildString {
        scripts.filter { it.isEnabled }.forEach { append(fallbackContent(it.resourceId)) }
    }

    if (!fetchRemote) return localContent

    val httpClient = HttpClient(OkHttp)
    return try {
        buildString {
            scripts.filter { it.isEnabled }.forEach { script ->
                val content = runCatching {
                    val response = httpClient.get(SCRIPT_SRC + script.scriptTitle)
                    if (response.status == HttpStatusCode.OK) response.body<String>()
                    else throw IllegalStateException("Unexpected script response")
                }.getOrElse { fallbackContent(script.resourceId) }
                append(content)
            }
        }
    } finally {
        httpClient.close()
    }
}
