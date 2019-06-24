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
        // Which scope?
        GlobalScope.launch {
            transport.captureEvent(event)
        }
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }
}