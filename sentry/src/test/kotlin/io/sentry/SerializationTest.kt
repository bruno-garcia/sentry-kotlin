package io.sentry

import com.google.gson.JsonObject
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 *  returns a regex for checking for a property serialized in json
 */
private fun getSerializedPropRegex(propName: String, propVal: String):Regex{
   return Regex(""""$propName"\s*:\s*"$propVal"""")
}

/**
 * Generic deserialization
 */
//private fun getValue(theObj: JsonObject, vararg path: String):Any{
//    if ( path.isEmpty()){
//        return theObj
//    }
//    val currentPath = path[0]
//
//    if ( path.size == 1){
//
//    }
//}



class SentrySerializationTest {
    @Test
    fun `should serialize a simple event`(){
        val evt = SentryEvent()

        val eventString = serializeEvent(evt)
        // TODO Rwn. remove
        println(eventString)
        val idRegex = getSerializedPropRegex("event_id", evt.eventId)
        assertTrue(idRegex.containsMatchIn(eventString),"could not find the eventId" )
        val timeStampRegex = getSerializedPropRegex("time_stamp", evt.timestamp)
        assertTrue(timeStampRegex.containsMatchIn(eventString),"could not find the timestamp" )
    }

    @Test
    fun `should serialize module elements`(){
        val evt = SentryEvent()
        evt.modules["module1"] = "hello"
        evt.modules["module2"] = "world"

        val eventString = serializeEvent(evt)
        println(eventString)


    }

}
