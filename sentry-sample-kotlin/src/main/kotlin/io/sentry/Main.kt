package io.sentry

fun main(args: Array<String>) {
    val eventId = SentryEvent().eventId
    println("EventId: $eventId)")
}