package io.sentry

// The static entry point to the SDK.
class Sentry {
    companion object {
//        private var sentryClient: AtomicReference<SentryClient> = AtomicReference(NoOpSentryClient.instance)

        @Volatile
        private var sentryClient: SentryClient = NoOpSentryClient.instance

        @JvmStatic
        fun init(configure: (SentryOptions) -> Unit) {
            var option = SentryOptions()
            configure(option)

            var client = this.sentryClient
            this.sentryClient = DefaultSentryClient(option)
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
//        fun captureEvent(event: SentryEvent): String = this.sentryClient.get().captureEvent(event)
    }
}