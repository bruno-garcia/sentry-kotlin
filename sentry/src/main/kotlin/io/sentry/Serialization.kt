package io.sentry

import com.google.gson.*

import java.lang.reflect.Type

fun serializeEvent(event: SentryEvent): String {

    val gsonb = GsonBuilder()
    gsonb.registerTypeAdapter(SentryEvent::class.java, SentryEventSerializer())
    gsonb.registerTypeAdapter(SentryException::class.java, SentryExceptionSerializer())
    gsonb.registerTypeAdapter(LogEntry::class.java, LogEntrySerializer())
    val gson = gsonb.create()
    return gson.toJson(event)
}

private class SentryEventSerializer : JsonSerializer<SentryEvent> {
    override fun serialize(src: SentryEvent?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        if (context == null) {
            throw Exception("Internal error. Serializer called without a context")
        }
        val jsonObj = JsonObject()
        if (src != null) {
            jsonObj.let {
                it.addProperty("event_id", src.eventId)
                it.addProperty("timestamp", src.timestamp)
                it.addProperty("logger", src.logger)
                it.addProperty("server_name", src.serverName)
                it.addProperty("release", src.release)
                if (src.exceptions?.isNotEmpty() == true) {
                    it.add("exceptions", context.serialize(src.exceptions))
                }
                if (src.modules?.isNotEmpty() == true) {
                    it.add("modules", context.serialize(src.modules))
                }
                if (src.logEntry != null) {
                    it.add("logentry", context.serialize(src.logEntry))
                }
            }
        }
        return jsonObj
    }
}

private class SentryExceptionSerializer : JsonSerializer<SentryException> {
    override fun serialize(src: SentryException?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        if (context == null) {
            throw Exception("Internal error. Serializer called without a context")
        }
        val jsonObj = JsonObject()
        if (src != null) {
            jsonObj.let {
                it.add("value", context.serialize(src.value))
                it.add("module", context.serialize(src.module))
                it.add("threadId", context.serialize(src.threadId))
                it.add("type", context.serialize(src.type))
            }
        }
        return jsonObj
    }
}

private class LogEntrySerializer : JsonSerializer<LogEntry> {
    override fun serialize(src: LogEntry?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        if (context == null) {
            throw Exception("Internal error. Serializer called without a context")
        }
        val jsonObj = JsonObject()
        if (src != null) {
            jsonObj.let {
                it.add("formatted", context.serialize(src.formatted))
                it.add("message", context.serialize(src.message))
                if (src.params?.isNotEmpty() == true) {
                    it.add("params", context.serialize(src.params))
                }
            }
        }
        return jsonObj
    }
}
