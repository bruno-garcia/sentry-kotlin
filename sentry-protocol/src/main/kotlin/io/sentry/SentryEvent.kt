package io.sentry
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.UUID

open class SentryEvent {
    // TODO: make internals visible to 'sentry' and mark this internal
    var throwable: Throwable? = null

    internal val eventUuid: UUID
    internal val eventInstant: Instant

    val eventId: String
        get() = eventUuid.toString().replace("-", "")
    val timestamp: String
        get() = DateTimeFormatter.ISO_INSTANT.format(eventInstant)

    // Scope data
    var tags: MutableMap<String, String> = mutableMapOf()
    var extra: MutableMap<String, Any> = mutableMapOf()
    var breadcrumbs: MutableList<Breadcrumb> = mutableListOf()
    var fingerprint: MutableList<String>? = null
    var request: SentryRequest? = null
    var context: SentryContext? = null

    var logEntry: LogEntry? = null
    var logger: String? = null
    var level: String = "error"
    var serverName: String? = null
    var release: String? = null
    var modules: MutableMap<String, String>? = null
    var exceptions: MutableList<SentryException>? = null
    val sdk: SdkVersion = SdkVersion()

    constructor() : this(null, null)

    // TODO: Make internal
    constructor(throwable: Throwable?) : this() {
        this.throwable = throwable
    }

    internal constructor(
        eventId: UUID?,
        eventInstant: Instant?
    ) {
        this.eventUuid = eventId ?: UUID.randomUUID()
        this.eventInstant = eventInstant ?: Instant.now()
    }
}

data class LogEntry(
    var message: String? = null,
    var formatted: String? = null,
    var params: MutableList<String>? = null
)

data class SdkVersion constructor(
    val name: String = "sentry.kotlin",
    val version: String = "0.0.0-alpha.000-really-alpha-0000001"
)
data class Breadcrumb(val name: String, val timestamp: Instant)
