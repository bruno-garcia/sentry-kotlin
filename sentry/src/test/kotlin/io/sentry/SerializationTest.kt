package io.sentry

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Generic accessor for tests
 */
private fun getValue(theObj: JsonObject, vararg path: String): Any? {
    if (path.isEmpty()) {
        throw Exception("null path in getValue")
    }
    val currentPath = path[0]

    val element = theObj.get(currentPath) ?: return null

    if (path.size == 1) {
        if (element is JsonPrimitive) {
            return when {
                element.isBoolean -> element.asBoolean
                element.isNumber -> element.asNumber
                element.isString -> element.asString
                else -> throw Exception("unsupported element type ${element.javaClass.name}")
            }
        }
        throw Exception("getValue can only navigate to primitive types")
    } else {
        if (element is JsonObject)
            return getValue(element, *path.drop(1).toTypedArray())
        throw Exception("expecting an object but got ${element.javaClass.name} at element $currentPath")
    }
}

private fun stringToJsonObj(serialized: String): JsonObject {
    return JsonParser().parse(serialized).asJsonObject
}

class SentrySerializationTest {
    @Test
    fun `should serialize a simple event`() {
        val evt = SentryEvent()
        val eventString = serializeEvent(evt)
        val jsonObj = stringToJsonObj(eventString)

        AssertEventSerialization(evt, jsonObj)
    }

    @Test
    fun `should serialize a complex event`() {
        val evt = SentryEvent().apply {
            logger = "the logger"
            serverName = "the serverName"
            release = "the release"
            logEntry = LogEntry("the message", "the formatted", mutableListOf("1", "2"))
        }
        val eventString = serializeEvent(evt)
        val jsonObj = stringToJsonObj(eventString)

        AssertEventSerialization(evt, jsonObj)
    }

    @Test
    fun `should serialize module elements`() {
        var modules = hashMapOf<String, String>()
        modules["module1"] = "hello"
        modules["module2"] = "world"

        val evt = SentryEvent()
        evt.modules = modules

        val eventString = serializeEvent(evt)
        val jsonObj = stringToJsonObj(eventString)

        assertEquals("hello", getValue(jsonObj, "modules", "module1"))
        assertEquals("world", getValue(jsonObj, "modules", "module2"))
    }

    private fun AssertEventSerialization(evt: SentryEvent, jsonObj: JsonObject) {
        assertEquals(evt.eventId, getValue(jsonObj, "event_id"))
        assertEquals(evt.timestamp, getValue(jsonObj, "timestamp"))
        assertEquals(evt.logger, getValue(jsonObj, "logger"))
        assertEquals(evt.serverName, getValue(jsonObj, "server_name"))
        assertEquals(evt.release, getValue(jsonObj, "release"))
        assertEquals(evt.logEntry?.formatted, getValue(jsonObj, "logentry", "formatted"))
        assertEquals(evt.logEntry?.message, getValue(jsonObj, "logentry", "message"))
    }
}
