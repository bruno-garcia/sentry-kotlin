package io.sentry

class SentryScope {
    val breakCrumbs: MutableList<BreadCrumb> = mutableListOf()
    var tags: MutableMap<String, String> = mutableMapOf()
    var extra: MutableMap<String, Any> = mutableMapOf()
}