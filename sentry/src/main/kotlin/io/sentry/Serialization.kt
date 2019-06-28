package io.sentry

import com.google.gson.*

import java.lang.reflect.Type
import java.time.format.DateTimeFormatter

fun serializeEvent(event: SentryEvent): String {

    val gsonb = GsonBuilder()
    gsonb.registerTypeAdapter(SentryEvent::class.java, SentryEventSerializer())
    gsonb.registerTypeAdapter(SentryException::class.java, SentryExceptionSerializer())
    gsonb.registerTypeAdapter(LogEntry::class.java, LogEntrySerializer())
    gsonb.registerTypeAdapter(SentryStackFrame::class.java, SentryStackFrameSerializer())
    gsonb.registerTypeAdapter(SentryStacktrace::class.java, SentryStacktraceSerializer())
    gsonb.registerTypeAdapter(SdkVersion::class.java, SdkVersionSerializer())
    gsonb.registerTypeAdapter(Breadcrumb::class.java, BreadcrumbSerializer())
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
                it.addProperty("level", src.level)
                it.addProperty("server_name", src.serverName)
                it.addProperty("release", src.release)
                it.add("sdk", context.serialize(src.sdk))
                if (src.exceptions?.isNotEmpty() == true) {
                    it.add("exception", context.serialize(src.exceptions))
                }
                if (src.modules?.isNotEmpty() == true) {
                    it.add("modules", context.serialize(src.modules))
                }
                if (src.logEntry != null) {
                    it.add("logentry", context.serialize(src.logEntry))
                }
                if (src.breadcrumbs.isNotEmpty()) {
                    it.add("breadcrumbs", context.serialize(src.breadcrumbs))
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
                it.add("stacktrace", context.serialize(src.stacktrace))
            }
        }
        return jsonObj
    }
}

private class SentryStacktraceSerializer : JsonSerializer<SentryStacktrace> {
    override fun serialize(src: SentryStacktrace?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        if (context == null) {
            throw Exception("Internal error. Serializer called without a context")
        }
        val jsonObj = JsonObject()
        if (src != null) {
            jsonObj.add("frames", context.serialize(src.frames))
        }
        return jsonObj
    }
}

private class SentryStackFrameSerializer : JsonSerializer<SentryStackFrame> {
    override fun serialize(src: SentryStackFrame?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        if (context == null) {
            throw Exception("Internal error. Serializer called without a context")
        }
        val jsonObj = JsonObject()
        if (src != null) {
            jsonObj.let {
                it.add("filename", context.serialize(src.filename))
                it.add("function", context.serialize(src.function))
                it.add("module", context.serialize(src.module))
                it.add("lineno", context.serialize(src.lineno))
                it.add("colno", context.serialize(src.colno))
                it.add("abs_path", context.serialize(src.absolutePath))
                it.add("context_line", context.serialize(src.contextLine))
                it.add("in_app", context.serialize(src.inApp))
                it.add("package", context.serialize(src.`package`))
                it.add("platform", context.serialize(src.platform))
                it.add("image_addr", context.serialize(src.imageAddress))
                it.add("symbol_addr", context.serialize(src.symbolAddress))
                it.add("instruction_offset", context.serialize(src.instructionOffset))

                if (src.preContext?.isNotEmpty() == true) {
                    it.add("pre_context", context.serialize(src.preContext))
                }
                if (src.postContext?.isNotEmpty() == true) {
                    it.add("post_context", context.serialize(src.postContext))
                }
                if (src.framesOmitted?.isNotEmpty() == true) {
                    it.add("frames_omitted", context.serialize(src.framesOmitted))
                }
                if (src.vars?.isNotEmpty() == true) {
                    it.add("vars", context.serialize(src.vars))
                }
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

private class SdkVersionSerializer : JsonSerializer<SdkVersion> {
    override fun serialize(src: SdkVersion?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        if (context == null) {
            throw Exception("Internal error. Serializer called without a context")
        }
        val jsonObj = JsonObject()
        if (src != null) {
            jsonObj.let {
                it.add("name", context.serialize(src.name))
                it.add("version", context.serialize(src.version))
            }
        }
        return jsonObj
    }
}

private class BreadcrumbSerializer : JsonSerializer<Breadcrumb> {
    override fun serialize(src: Breadcrumb?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        if (context == null) {
            throw Exception("Internal error. Serializer called without a context")
        }
        val jsonObj = JsonObject()
        if (src != null) {
            jsonObj.let {
                it.add("name", context.serialize(src.name))
                it.add("timestamp", context.serialize(DateTimeFormatter.ISO_INSTANT.format(src.timestamp)))
            }
        }
        return jsonObj
    }
}
