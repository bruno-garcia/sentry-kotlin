package io.sentry

import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.Test

class SentryEventTest {
    @Test
    fun divisionByZeroError() {
        assertEquals("/ by zero",
            assertFailsWith<ArithmeticException> {
                1 / 0
            }.message)
    }
}
