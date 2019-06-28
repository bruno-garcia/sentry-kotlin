package io.sentry

class SentryScope constructor() {
    val breakcrumbs: MutableList<Breadcrumb> = mutableListOf()
    var tags: MutableMap<String, String> = mutableMapOf()
    var extra: MutableMap<String, Any> = mutableMapOf()

    private constructor(other: SentryScope) : this() {
        breakcrumbs.addAll(other.breakcrumbs)
        tags.putAll(other.tags)
        extra.putAll(other.extra)
    }

    fun clone() = SentryScope(this)
}