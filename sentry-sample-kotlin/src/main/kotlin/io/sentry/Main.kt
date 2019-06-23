package io.sentry

fun main() {
    val event = SentryEvent()
    println("EventId: ${event.eventId})")
    println("Timestamp: ${event.timestamp}")
}