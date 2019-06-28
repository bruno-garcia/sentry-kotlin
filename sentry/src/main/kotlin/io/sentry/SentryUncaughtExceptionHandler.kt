package io.sentry

internal class SentryUncaughtExceptionHandler : Thread.UncaughtExceptionHandler {
    private var originalHandler: Thread.UncaughtExceptionHandler? = Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(thread: Thread?, throwable: Throwable?) {
            val event = SentryEvent().apply {
                logEntry = LogEntry().apply {
                    formatted = throwable?.message
//                    level = "fatal"
                }
            }
                Sentry.captureEvent(event)

        if (originalHandler != null) {
            originalHandler?.uncaughtException(thread, throwable)
        } else if (throwable !is ThreadDeath) {
            System.err.print("""Exception in thread "${thread?.name}" """)
            throwable?.printStackTrace(System.err)
        }
    }

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    fun unregister() {
        val currentHandler = Thread.getDefaultUncaughtExceptionHandler()
        if (currentHandler === this) {
            Thread.setDefaultUncaughtExceptionHandler(currentHandler)
        }
    }
}