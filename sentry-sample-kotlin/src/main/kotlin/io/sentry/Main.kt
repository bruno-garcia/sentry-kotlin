package io.sentry

fun main() {
    val event = SentryEvent()
    event.message = "Sample event from Kotlin"

    println("EventId: ${event.eventId})")
    println("Timestamp: ${event.timestamp}")
    println("Message: ${event.message}")

    Sentry.init { o -> o.release = "6858af2" }
    Sentry.captureEvent(event)
}