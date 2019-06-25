package io.sentry

interface Transport {
    suspend fun captureEvent(event: SentryEvent): String
    suspend fun flush()
    suspend fun close()
}
