package io.sentry

import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

class SentryEventTest {
    @Test
    fun `constructor creates event id without dashes`() = assertFalse(SentryEvent().eventId.contains("-"))

    @Test
    fun `constructor creates a non empty event id`() =
        assertNotEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), SentryEvent().eventUuid)
}
