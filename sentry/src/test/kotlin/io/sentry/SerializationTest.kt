package io.sentry

import kotlin.test.Test
import kotlin.test.assertTrue

/**
 *  returns a regex for checking for a property serialized in json
 */
private fun getSerializedPropRegex(propName: String, propVal: String):Regex{
   return Regex(""""$propName"\s*:\s*"$propVal"""")
}

class SentrySerializationTest {
    @Test
    fun `should serialize a simple event`(){
        val evt = SentryEvent()

        val result = serializeEvent(evt)
        // TODO Rwn. remove
        println(result)
        val idRegex = getSerializedPropRegex("event_id", evt.eventId)
        assertTrue(idRegex.containsMatchIn(result),"could not find the eventId" )
        val timeStampRegex = getSerializedPropRegex("time_stamp", evt.timestamp)
        assertTrue(timeStampRegex.containsMatchIn(result),"could not find the timestamp" )
    }
}
