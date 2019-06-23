package io.sentry

interface SentryClient {
    fun captureEvent(event: SentryEvent): String
}

class DefaultSentryClient : SentryClient {
    override fun captureEvent(event: SentryEvent): String {
        return event.eventId
    }
}