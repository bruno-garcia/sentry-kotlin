package io.sentry

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

import kotlin.coroutines.*
import kotlin.system.measureTimeMillis

// import kotlin.system.*

class SentryHubTest {
    @Test
    fun `Scopes are separated in various `() {
        val time1 = measureTimeMillis {
            runBlocking {
                coroutineScope {
                    launch {
                        println("scope1 start")
                        delay(100)
                        println("scope1 end")
                    }
                    launch {
                        println("scope2 start")
                        delay(100)
                        println("scope2 end")
                    }
                }
            }
        }
        println("Test 1 time $time1")
    }

    @Test
    fun `test2`() {
        val time1 = measureTimeMillis {
            runBlocking {
                coroutineScope {
                    launch {
                        println("scope1 start")
                        delay(100)
                        println("scope1 end")
                    }
                }

                coroutineScope {
                    launch {
                        println("scope2 start")
                        delay(100)
                        println("scope2 end")
                    }
                }
            }
        }
        println("Test 2 time $time1")
    }

    @Test
    fun testScope() {

        Sentry.init { o ->
            o.dsn = "https://5fd7a6cda8444965bade9ccfd3df9882@sentry.io/1188141"
            o.release = "6858af2"
        }

        var event: SentryEvent?

        runBlocking {
            Sentry.withScope {
                Sentry.addBreadcrumb("Scope 1 hello")
                Sentry.addBreadcrumb("Scope 1 world")

                event = SentryEvent().apply {
                    logEntry = LogEntry(formatted = "Sample event from Kotlin Scope 1")
                    logger = "Kotlin-main"
                    release = "6858af2"
                }
                Sentry.captureEvent(event!!)
            }
            Sentry.withScope {
                Sentry.addBreadcrumb("Scope 2 hello")
                Sentry.addBreadcrumb("Scope 2 world")

                event = SentryEvent().apply {
                    logEntry = LogEntry(formatted = "Sample event from Kotlin Scope 1")
                    logger = "Kotlin-main"
                    release = "6858af2"
                }
                Sentry.captureEvent(event!!)
            }

            // no scope
            event = SentryEvent().apply {
                logEntry = LogEntry(formatted = "Sample event from Kotlin Scope 1")
                logger = "Kotlin-main"
                release = "6858af2"
            }
            Sentry.captureEvent(event!!)
        }
    }
}