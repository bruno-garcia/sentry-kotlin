package io.sentry
import kotlinx.coroutines.*

internal interface BackgroundWorker {
    fun enqueueEvent(event: SentryEvent): Boolean
}

internal class DefaultBackgroundWorker constructor(
    private val options: SentryOptions,
    private val transport: Transport
) : BackgroundWorker {

    override fun enqueueEvent(event: SentryEvent): Boolean {
        // TODO: Write to in memory queue. Drop event if queue is full
        // Let worker thread read off of the queue and asynchronously flush events to ITransport

        // Which scope?
        // Scope should be the lifetime of this worker. Meaning if the worker is disposed
        // ideally jobs should flush/dispose block until then, with a timeout.
        runBlocking { // but this expression blocks the main thread
            GlobalScope.launch {
                transport.captureEvent(event)
            }
        }
        return true
    }
}