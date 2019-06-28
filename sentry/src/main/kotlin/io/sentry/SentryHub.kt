package io.sentry

interface SentryHub : SentryClient {
    fun addBreadcrumb(breadcrumb: Breadcrumb)
    fun pushScope(): SentryHub
    fun addTag(key: String, value: String)
}

// TODO RaduW at the moment this is not thread safe, need to look at Kotlin concurrency primitives
class DefaultSentryHub constructor(private val client: SentryClient) : SentryHub {

    private val scopeStack = mutableListOf<SentryScope>(SentryScope())

    override fun close() = client.close()
    override fun captureEvent(event: SentryEvent): String {
        val enhancedEvent = enhanceEvent(event)
        return client.captureEvent(enhancedEvent)
    }

    private constructor(hub: DefaultSentryHub) : this(hub.client) {
        scopeStack.clear()
        scopeStack.addAll(hub.scopeStack)
    }

    private fun enhanceEvent(event: SentryEvent): SentryEvent {
        if (scopeStack.size > 0) {

            val scope = scopeStack.last()

            // TODO Decide what is the correct behavior when the user adds breadcrumbs to the
            // event and we also have breadcrumbs in scope (a sorted merge based on timestamp ?)
            // I (RaduW) I would not have events directly settable on SentryEvent
            if (scope.breakcrumbs.isNotEmpty() && event.breadcrumbs.isEmpty()) {
                event.breadcrumbs = scope.breakcrumbs.toMutableList()
            }

            // TODO Decide what is the correct behavior for merging extra
            if (scope.extra.isNotEmpty() && event.extra.isEmpty()) {
                event.extra = scope.extra.toMutableMap()
            }

            // TODO Decide what is the correct behavior for merging tags
            if (scope.tags.isNotEmpty() && event.tags.isEmpty()) {
                event.tags = scope.tags.toMutableMap()
            }
        }
        // enhance event with scope data
        return event
    }

    override fun addBreadcrumb(breadcrumb: Breadcrumb) {
        val scope = scopeStack.last()
        scope.breakcrumbs.add(breadcrumb)
    }

    override fun addTag( key:String, value: String){
        val scope = scopeStack.last()
        scope.tags[key] = value
    }


        override fun pushScope(): SentryHub {
        val newHub = DefaultSentryHub(this)
        val scope = scopeStack.last()
        val newScope = scope.clone()
        scopeStack.add(newScope)
        return newHub
    }
}
