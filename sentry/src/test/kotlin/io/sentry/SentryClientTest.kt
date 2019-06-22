package io.sentry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SentryClientTest {
    @Test
    fun divisionByZeroError() {
        assertEquals("/ by zero",
            assertThrows<ArithmeticException> {
                1 / 0
            }.message)
    }
}
