package io.sentry

interface SentryClient {
    fun captureEvent(event: SentryEvent) {

    }
}