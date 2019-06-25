package io.sentry

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Generic accessor for tests
 */
private fun getValue(theObj: JsonObject, vararg path: String): Any {
    if (path.isEmpty()) {
        throw Exception("null path in getValue")
    }
    val currentPath = path[0]

    val element = theObj.get(currentPath) ?: throw Exception("could not find member at path $currentPath")

    if (path.size == 1) {
        if (element is JsonPrimitive){
            when {
                element.isBoolean -> return element.asBoolean
                element.isNumber -> return element.asNumber
                element.isString -> return element.asString
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

private fun stringToJsonObj( serialized: String): JsonObject {
    return JsonParser().parse(serialized).asJsonObject
}


class SentrySerializationTest {
    @Test
    fun `should serialize a simple event`() {
        val evt = SentryEvent()
        val eventString = serializeEvent(evt)
        val jsonObj = stringToJsonObj(eventString)

        assertEquals(evt.eventId, getValue(jsonObj, "event_id"))
        assertEquals(evt.timestamp, getValue(jsonObj, "time_stamp"))
    }

    @Test
    fun `should serialize module elements`() {
        val evt = SentryEvent()
        evt.modules["module1"] = "hello"
        evt.modules["module2"] = "world"

        val eventString = serializeEvent(evt)
        val jsonObj = stringToJsonObj(eventString)

        assertEquals("hello",  getValue(jsonObj,"modules", "module1"))
        assertEquals("world",  getValue(jsonObj,"modules", "module2"))

    }

}
