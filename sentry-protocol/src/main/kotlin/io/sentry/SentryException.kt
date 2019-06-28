package io.sentry

class SentryException {
    var type: String? = null
    var value: String? = null
    var module: String? = null
    var threadId: String? = null
    var stacktrace: SentryStacktrace? = null
    var mechanism: Mechanism? = null
    var data: HashMap<String, Any>? = null
}

data class SentryStacktrace constructor(val frames: MutableList<SentryStackFrame>)

class SentryStackFrame {
    var filename: String? = null
    var function: String? = null
    var platform: String? = null
    var module: String? = null
    var lineno: Int? = null
    var colno: Int? = null
    var absolutePath: String? = null
    var contextLine: String? = null
    var preContext: MutableList<String>? = null
    var postContext: MutableList<String>? = null
    var inApp: Boolean? = null
    var vars: HashMap<String, String>? = null
    var framesOmitted: MutableList<Int>? = null
    var `package`: String? = null
    var imageAddress: Long? = null
    var symbolAddress: Long? = null
    var instructionOffset: Long? = null
}

class Mechanism {
    var type: String? = null
    var description: String? = null
    var helpLink: String? = null
    var handled: Boolean? = null
    var meta: HashMap<String, Any>? = null
    var data: HashMap<String, Any>? = null
}