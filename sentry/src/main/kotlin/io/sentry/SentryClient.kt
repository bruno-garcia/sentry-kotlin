package io.sentry

import java.net.InetAddress
import java.security.InvalidParameterException

interface SentryClient {
    fun captureEvent(event: SentryEvent): String
    fun close()
}

class DefaultSentryClient constructor(private val options: SentryOptions) : SentryClient {

    private val worker: DefaultBackgroundWorker
    private val uncaughtHandler: SentryUncaughtExceptionHandler

    init {
        if (options.parsedDsn == null) {
            throw InvalidParameterException("Options doesn't contain a DSN.")
        }

        uncaughtHandler = SentryUncaughtExceptionHandler()
        worker = DefaultBackgroundWorker(options, HttpTransport(::serializeEvent, options.parsedDsn!!))
    }

    override fun close() {
        uncaughtHandler.unregister()
        TODO("not implemented")
    }

    override fun captureEvent(event: SentryEvent): String {
        if (event.release == null && options.release != null) {
            event.release = options.release
        }

        if (event.serverName == null) {
            event.serverName = InetAddress.getLocalHost().hostName
        }

//        // TODO: Run exception processors against the throwable
//        val ex = event.throwable?.toSentryException()
//        if (ex != null) {
//            if (event.exceptions != null) {
//                event.exceptions!!.add(ex)
//            } else {
//                event.exceptions = mutableListOf(ex)
//            }
//        }
        worker.enqueueEvent(event)
        return event.eventId
    }
}

internal class NoOpSentryClient private constructor() : SentryClient {
    override fun captureEvent(event: SentryEvent): String = EMPTY_EVENT_ID
    override fun close() {}

    companion object {
        var instance: NoOpSentryClient = NoOpSentryClient()
    }
}
