package io.sentry

import com.google.gson.*

import java.lang.reflect.Type

fun serializeEvent(event: SentryEvent): String {

    val gsonb = GsonBuilder()
    gsonb.registerTypeAdapter(SentryEvent::class.java, SentryEventSerializer())
    val gson = gsonb.create()
    return gson.toJson(event)
}

private class SentryEventSerializer : JsonSerializer<SentryEvent> {
    override fun serialize(src: SentryEvent?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        if (context == null)
            throw Exception("Internal error. Serializer called without a context")
        val jsonObj = JsonObject()
        if (src != null) {
            jsonObj.let {
                it.addProperty("event_id", src.eventId)
                it.addProperty("timestamp", src.timestamp)
                if (src.modules?.isNotEmpty() == true) {
                    it.add("modules", context.serialize(src.modules))
                }
            }
        }
        return jsonObj
    }
}
