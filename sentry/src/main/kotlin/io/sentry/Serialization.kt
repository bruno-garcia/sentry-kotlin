package io.sentry

import com.google.gson.*

import java.lang.reflect.Type

fun serializeEvent(event: SentryEvent): String {

    val gsonb = GsonBuilder()
    gsonb.registerTypeAdapter(SentryEvent::class.java, SentryEventSerializer())
    val gson = gsonb.create()
    return gson.toJson(event)
}


class SentryEventSerializer : JsonSerializer<SentryEvent> {
    override fun serialize(src: SentryEvent?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        val retVal = JsonObject()
        if ( src != null){
            retVal.addProperty("event_id", src.eventId)
            retVal.addProperty("time_stamp", src.timestamp)
        }
        return retVal
    }
}


