package io.sentry
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.UUID

open class SentryEvent {
    internal val eventUuid: UUID
    internal val eventInstant: Instant

    val eventId: String
        get() = eventUuid.toString().replace("-", "")
    val timestamp: String
        get() = DateTimeFormatter.ISO_INSTANT.format(eventInstant)

    constructor() : this(null, null)

    internal constructor(
        eventId: UUID?,
        eventInstant: Instant?
    ) {
        this.eventUuid = eventId ?: UUID.randomUUID()
        this.eventInstant = eventInstant ?: Instant.now()
    }
}
