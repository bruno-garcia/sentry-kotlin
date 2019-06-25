package io.sentry
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.UUID

open class SentryEvent {
    internal val eventUuid: UUID
    internal val eventInstant: Instant

    var message: String? = null

    public val eventId: String
        get() = eventUuid.toString().replace("-", "")
    public val timestamp: String
        get() = DateTimeFormatter.ISO_INSTANT.format(eventInstant)

    public val modules: MutableMap<String, String> = HashMap()

    constructor() : this(null, null)

    internal constructor(
        eventId: UUID?,
        eventInstant: Instant?
    ) {
        this.eventUuid = eventId ?: UUID.randomUUID()
        this.eventInstant = eventInstant ?: Instant.now()
    }
}
