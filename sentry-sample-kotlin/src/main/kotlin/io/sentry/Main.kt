package io.sentry

import javax.management.InvalidApplicationException

fun main() {
    val event = SentryEvent().apply {
        logEntry = LogEntry(formatted = "Sample event from Kotlin")
        logger = "Kotlin-main"
        release = "6858af2"
    }

    println("EventId: ${event.eventId})")
    println("Timestamp: ${event.timestamp}")

    Sentry.init { o ->
        o.dsn = "https://5fd7a6cda8444965bade9ccfd3df9882@sentry.io/1188141"
        o.release = "6858af2"
    }

    Sentry.captureEvent(event)

    try {
        crappyFunction()
    } catch (throwable: Throwable) {
        Sentry.captureException(throwable)

        // rethrown and crash the app!
        throw throwable
    }
}

fun crappyFunction(): Nothing = throw InvalidApplicationException("Test exception.")
