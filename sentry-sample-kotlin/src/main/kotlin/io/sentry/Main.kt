package io.sentry

import kotlinx.coroutines.runBlocking
import javax.management.InvalidApplicationException

fun main() {
    Sentry.init { o ->
//        o.dsn = "https://5fd7a6cda8444965bade9ccfd3df9882@sentry.io/1188141"
        o.dsn = "https://d29a5a7b9d8d4b4591d4e5bd434fe393@sentry.io/24969"
        o.release = "6858af2"
    }

    runBlocking {
        Sentry.addTag("a", "external A")
        Sentry.addTag("b", "external B")
        Sentry.withScope {
            Sentry.addBreadcrumb("Scope 1 hello")
            Sentry.addBreadcrumb("Scope 1 world")

            Sentry.addTag("a", "scope 1 A")
            Sentry.addTag("c", "scope 1 C")


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

            Sentry.addTag("a", "scope 2 A")
            Sentry.addTag("d", "scope 2 D")

            val second = SentryEvent().apply {
                logEntry = LogEntry(formatted = "Sample event from Kotlin Scope 2")
                logger = "Kotlin-main"
                release = "6858af2"
            }
            Sentry.captureEvent(second)
        }

        //override the external A
        Sentry.addTag("a", "external A overridden")
        Sentry.addTag("e", "external E")


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
