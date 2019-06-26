package io.sentry

fun main() {
    val event = SentryEvent()

    event.logEntry = LogEntry(formatted = "Sample event from Kotlin")

    println("EventId: ${event.eventId})")
    println("Timestamp: ${event.timestamp}")
    println("Timestamp: ${event.logEntry}")

    Sentry.init { o -> o.release = "6858af2" }
    Sentry.captureEvent(event)
}