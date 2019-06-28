package io.sentry

fun main() {
    val event = SentryEvent()

    event.logEntry = LogEntry(formatted = "Sample event from Kotlin")

    println("EventId: ${event.eventId})")
    println("Timestamp: ${event.timestamp}")
    println("Timestamp: ${event.logEntry}")

    Sentry.init { o ->
        o.release = "6858af2"
        o.dsn = "https://5fd7a6cda8444965bade9ccfd3df9882@sentry.io/1188141"
    }
    Sentry.captureEvent(event)
}