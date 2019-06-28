package io.sentry

fun Throwable?.toSentryException(): SentryException? {

    if (this == null) {
        return null
    }

    val ex = SentryException()
    ex.type = this.javaClass.name
    ex.module = this.javaClass.getPackage()?.name
    ex.value = this.message

    val frames = this.stackTrace?.map {
        SentryStackFrame().apply {
            filename = it.fileName
            lineno = it.lineNumber
            filename = it.fileName
            function = it.methodName
            // TODO: Intellij sees it but gradlew fails to compile
            // module = it.moduleName
        }
    }

    if (frames?.isNotEmpty() == true) {
        ex.stacktrace = SentryStacktrace(frames)
    }

    return ex
}
