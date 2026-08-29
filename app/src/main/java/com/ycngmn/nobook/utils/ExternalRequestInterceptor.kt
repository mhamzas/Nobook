package com.ycngmn.nobook.utils

import android.net.Uri
import com.multiplatform.webview.request.RequestInterceptor
import com.multiplatform.webview.request.WebRequest
import com.multiplatform.webview.request.WebRequestInterceptResult
import com.multiplatform.webview.web.WebViewNavigator

private val INTERNAL_HOSTS = setOf(
    "facebook.com",
    "www.facebook.com",
    "m.facebook.com",
    "lm.facebook.com",
    "messenger.com",
    "www.messenger.com"
)

private fun isInternalHost(host: String?): Boolean {
    val normalizedHost = host?.lowercase()?.removePrefix("www.") ?: return false
    return normalizedHost in INTERNAL_HOSTS ||
        normalizedHost.endsWith(".facebook.com") ||
        normalizedHost.endsWith(".messenger.com")
}

class ExternalRequestInterceptor(
    private val handleExternalUrl: (String) -> Unit
) : RequestInterceptor {
    override fun onInterceptUrlRequest(
        request: WebRequest,
        navigator: WebViewNavigator
    ): WebRequestInterceptResult {
        val uri = Uri.parse(request.url)
        return if (request.isForMainFrame && isInternalHost(uri.host)) {
            WebRequestInterceptResult.Allow
        } else if (!request.isForMainFrame) {
            WebRequestInterceptResult.Allow
        } else {
            handleExternalUrl(fbRedirectSanitizer(request.url))
            WebRequestInterceptResult.Reject
        }
    }
}
