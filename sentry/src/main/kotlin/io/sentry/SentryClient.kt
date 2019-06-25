package io.sentry

interface SentryClient {
    fun captureEvent(event: SentryEvent): String
    fun close()
}

class DefaultSentryClient constructor(private val options: SentryOptions) : SentryClient {

    private val worker: DefaultBackgroundWorker = DefaultBackgroundWorker(options, HttpTransport())

    override fun close() {
        TODO("not implemented")
    }

    override fun captureEvent(event: SentryEvent): String {
        worker.enqueueEvent(event)
        return event.eventId/**/
    }
}

internal class NoOpSentryClient private constructor() : SentryClient {
    override fun captureEvent(event: SentryEvent): String = EMPTY_EVENT_ID
    override fun close() {}

    companion object {
        var instance: NoOpSentryClient = NoOpSentryClient()
    }
}
