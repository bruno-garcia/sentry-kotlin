package io.sentry
import java.sql.Timestamp
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.UUID

data class BreadCrumb(val name: String, val timestamp: Timestamp)

open class SentryEvent {
    internal val eventUuid: UUID
    internal val eventInstant: Instant

    val eventId: String
        get() = eventUuid.toString().replace("-", "")
    val timestamp: String
        get() = DateTimeFormatter.ISO_INSTANT.format(eventInstant)

    // Scope data
    var tags: MutableMap<String, String> = mutableMapOf()
    var extra: MutableMap<String, Any> = mutableMapOf()
    var breadCrumb: MutableList<BreadCrumb> = mutableListOf()
    var fingerprint: MutableList<String>? = null
    var request: SentryRequest? = null
    var context: SentryContext? = null

    var logEntry: LogEntry? = null
    var logger: String? = null
    var serverName: String? = null
    var release: String? = null
    var modules: MutableMap<String, String>? = null
    var exceptions: MutableList<SentryException>? = null

    constructor() : this(null, null)

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
