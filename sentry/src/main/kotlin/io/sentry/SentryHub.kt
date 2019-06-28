package io.sentry


interface SentryHub : SentryClient {

}


// TODO RaduW at the moment this is not thread safe, need to look at Kotlin concurrency primitives
class DefaultSentryHub constructor(private val client: SentryClient) : SentryHub {

    private val scopeStack = mutableListOf<SentryScope>()

    override fun close() = client.close()
    override fun captureEvent(event: SentryEvent): String {
        val enhancedEvent = enhanceEvent(event)
        return client.captureEvent(enhancedEvent)
    }

    private fun enhanceEvent(event: SentryEvent): SentryEvent {
        if ( scopeStack.size > 0){

            val scope = scopeStack.last()

            // TODO Decide what is the correct behavior when the user adds breadcrumbs to the
            // event and we also have breadcrumbs in scope (a sorted merge based on timestamp ?)
            // I (RaduW) I would not have events directly settable on SentryEvent
            if (scope.breakCrumbs.isNotEmpty() && event.breadCrumb.isEmpty() ) {
                event.breadCrumb = scope.breakCrumbs.toMutableList()
            }

            // TODO Decide what is the correct behavior for merging extra
            if ( scope.extra.isNotEmpty() && event.extra.isEmpty()){
                event.extra = scope.extra.toMutableMap()
            }

            // TODO Decide what is the correct behavior for merging tags
            if ( scope.tags.isNotEmpty() && event.tags.isEmpty()){
                event.tags = scope.tags.toMutableMap()
            }
        }
        //enhance event with scope data
        return event
    }


}