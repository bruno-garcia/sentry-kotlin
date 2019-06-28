package io.sentry

class SentryScope {
    val breakcrumbs: MutableList<Breadcrumb> = mutableListOf()
    var tags: MutableMap<String, String> = mutableMapOf()
    var extra: MutableMap<String, Any> = mutableMapOf()
}