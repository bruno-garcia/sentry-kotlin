package io.sentry

class SentryRequest {
    val url: String? = null
    val method: String? = null
    val data: Any? = null
    val queryString: String? = null
    val cookies: String? = null
    var headers: MutableMap<String, String>? = null
    var env: MutableMap<String, String>? = null
    var other: MutableMap<String, String>? = null
}
