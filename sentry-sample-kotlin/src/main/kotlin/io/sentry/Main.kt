package io.sentry

import kotlinx.coroutines.runBlocking
import javax.management.InvalidApplicationException

fun main() {
    Sentry.init { o ->
        o.dsn = "https://5fd7a6cda8444965bade9ccfd3df9882@sentry.io/1188141"
        o.release = "6858af2"
    }

    runBlocking {
        Sentry.withScope {
            Sentry.addBreadcrumb("Scope 1 hello")
            Sentry.addBreadcrumb("Scope 1 world")

            val first = SentryEvent().apply {
                logEntry = LogEntry(formatted = "Sample event from Kotlin Scope 1")
                logger = "Kotlin-main"
                release = "6858af2"
            }
            Sentry.captureEvent(first)
        }
        Sentry.withScope {
            Sentry.addBreadcrumb("Scope 2 hello")
            Sentry.addBreadcrumb("Scope 2 world")

            val second = SentryEvent().apply {
                logEntry = LogEntry(formatted = "Sample event from Kotlin Scope 2")
                logger = "Kotlin-main"
                release = "6858af2"
            }
            Sentry.captureEvent(second)
        }

        // no scope
        val third = SentryEvent().apply {
            logEntry = LogEntry(formatted = "Sample event from Kotlin without Scope")
            logger = "Kotlin-main"
            release = "6858af2"
        }
        Sentry.captureEvent(third)
    }

    try {
        crappyFunction()
    } catch (throwable: Throwable) {
        Sentry.captureException(throwable)

        // rethrown and crash the app!
        throw throwable
    }
}

fun crappyFunction(): Nothing = throw InvalidApplicationException("Test exception.")
