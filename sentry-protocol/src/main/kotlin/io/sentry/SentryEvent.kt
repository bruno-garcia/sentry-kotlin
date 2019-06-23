package io.sentry
import java.util.UUID

open class SentryEvent {
    internal var eventUuid: UUID
    val eventId: String
        get() = eventUuid.toString().replace("-", "")

    internal constructor(eventId: UUID?) {
        this.eventUuid = eventId ?: UUID.randomUUID()
    }

    constructor() : this(UUID.randomUUID())
}
