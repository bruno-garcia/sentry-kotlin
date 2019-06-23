package io.sentry

fun main() {
    val event = SentryEvent()
    event.message = "Sample event from Kotlin"

    println("EventId: ${event.eventId})")
    println("Timestamp: ${event.timestamp}")
    println("Message: ${event.message}")

    Sentry.init()
    Sentry.captureEvent(event)
}