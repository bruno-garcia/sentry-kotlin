package io.sentry

internal interface Transport {
    suspend fun captureEvent(event: SentryEvent): String
    suspend fun flush()
    suspend fun close()
}

internal class HttpTransport : Transport {
    override suspend fun close() {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun flush() {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun captureEvent(event: SentryEvent): String {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }
}