package io.sentry

import java.lang.reflect.Field
import java.net.InetAddress
import java.security.InvalidParameterException
import java.util.Vector

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
        val ex = event.throwable?.toSentryException()
        if (ex != null) {
            if (event.exceptions != null) {
                event.exceptions!!.add(ex)
            } else {
                event.exceptions = mutableListOf(ex)
            }
        }

        if (event.modules == null) {
            val modules = mutableMapOf<String, String>()
            val field: Field
            try {
                field = ClassLoader::class.java.getDeclaredField("classes")
                field.isAccessible = true
                val classLoader = Thread.currentThread().contextClassLoader
                @SuppressWarnings("unchecked")
                val classes = field.get(classLoader) as Vector<Class<*>>

                for (cls in classes) {
                    val location = cls.getResource('/'.toString() + cls.name.replace('.', '/') + ".class")
                        ?: continue
                    modules[location.toString().substringAfterLast("/")] = location.toString()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            event.modules = modules
        }

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
