package io.sentry

import java.time.Instant
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.test.*

class SentryEventTest {
    @Test
    fun `constructor creates event id without dashes`() = assertFalse(SentryEvent().eventId.contains("-"))

    @Test
    fun `constructor creates a non empty event id`() =
        assertNotEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), SentryEvent().eventUuid)

    @Test
    fun `constructor defines timestamp after now`() =
        assertTrue(Instant.now().plus(1, ChronoUnit.HOURS).isAfter(SentryEvent().eventInstant))

    @Test
    fun `constructor defines timestamp before hour ago`() =
        assertTrue(Instant.now().minus(1, ChronoUnit.HOURS).isBefore(SentryEvent().eventInstant))

    @Test
    fun `timestamp is formatted in ISO 8601 in UTC with Z format`() {
        // Sentry expects this format:
        val expected = "2000-12-31T23:59:59Z"
        val formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX", Locale.ROOT)
        val date = OffsetDateTime.parse(expected, formatter)
        assertEquals(expected, SentryEvent(null, date.toInstant()).timestamp)
    }
}
