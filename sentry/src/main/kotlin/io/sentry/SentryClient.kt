package io.sentry

class SentryEvent(
    var name: String? = null,
    var age: Int? = null)

interface SentryClient {
    fun captureEvent() {

    }
}