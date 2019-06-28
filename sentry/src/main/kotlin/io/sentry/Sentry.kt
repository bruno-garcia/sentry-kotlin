package io.sentry

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import java.sql.Timestamp

// The static entry point to the SDK.
class Sentry {
    companion object {
//        private var sentryClient: AtomicReference<SentryClient> = AtomicReference(NoOpSentryClient.instance)

        private var hubWrapper: ThreadLocal<SentryHub> = ThreadLocal()
            get(){
                if ( field.get() == null){
                    val hub = DefaultSentryHub(sentryClient)
                    field.set(hub)
                }
                return field
            }

        @Volatile
        private var sentryClient: SentryClient = NoOpSentryClient.instance

        @JvmStatic
        fun init(
            configure: (SentryOptions) ->
            // https://youtrack.jetbrains.com/issue/KT-21018
            // @JvmVoid
            Unit
        ) {
            var options = SentryOptions()
            configure(options)
            init(options)
        }

        @JvmStatic
        fun init(options: SentryOptions) {
            var client = this.sentryClient
            // TODO: Locate DSN (i.e env var) if one wasn't provided
            this.sentryClient = DefaultSentryClient(options)
            client.close()

//            val client = this.sentryClient.compareAndExchange(DefaultSentryClient(), NoOpSentryClient.instance)
//            if (client != NoOpSentryClient.instance) {
//                // TODO: warn init was called on a already initialized SDK
//                client.close()
//            }
        }

        @JvmStatic
        fun close() {
            var client = this.sentryClient
            this.sentryClient = NoOpSentryClient.instance
            client.close()

//            val client = sentryClient.compareAndExchange(NoOpSentryClient.instance, DefaultSentryClient())
//            client.close()
        }

        @JvmStatic
        fun captureEvent(event: SentryEvent): String = this.sentryClient.captureEvent(event)

        @JvmStatic
        fun captureException(exception: Throwable): String = captureEvent(SentryEvent(exception))


        @JvmStatic
        fun addBreadcrumb( message: String): Unit {
            val hub = hubWrapper.get()
            val breadcrumb = Breadcrumb(message, Timestamp(System.currentTimeMillis()))
            hub.addBreadcrumb(breadcrumb)
        }

        suspend fun withScope(block: CoroutineScope.() -> Unit) {

            var hub = hubWrapper.get()
            if (hub == null) {
                // no hub in this context create one
                hub = DefaultSentryHub(Sentry.sentryClient)
                hubWrapper.set(hub)
            }
            coroutineScope {
                block()
            }
        }
    }
}