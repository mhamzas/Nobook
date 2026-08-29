package com.ycngmn.nobook.utils

import android.content.Context
import android.webkit.WebView
import com.ycngmn.nobook.R

/** Optional helper for experiments; callers must explicitly opt in. */
object AntiReloadInjector {
    private var cachedScript: String? = null

    fun isFacebookHost(host: String?): Boolean {
        val normalized = host?.lowercase()?.removePrefix("www.") ?: return false
        return normalized == "facebook.com" || normalized.endsWith(".facebook.com")
    }

    fun injectIfEnabled(
        context: Context,
        webView: WebView,
        host: String?,
        enabled: Boolean
    ) {
        if (!enabled || !isFacebookHost(host)) return
        val script = cachedScript ?: context.resources.openRawResource(R.raw.anti_reload)
            .bufferedReader()
            .use { it.readText() }
            .also { cachedScript = it }
        webView.evaluateJavascript(script, null)
    }
}
