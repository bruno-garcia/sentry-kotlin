package io.sentry

class SentryOptions {
    // Expected to be defined by internal types that take option and require dsn
    internal var parsedDsn: Dsn? = null
    var dsn: String? = null
        set(value) {
            if (value == null) {
                parsedDsn = null
            } else {
                parsedDsn = Dsn(value)
            }
        }
    var release: String? = null
}