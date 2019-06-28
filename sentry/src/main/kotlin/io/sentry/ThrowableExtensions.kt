package io.sentry

fun Throwable?.toSentryException(): SentryException? {

    if (this == null) {
        return null
    }

    val ex = SentryException()
    ex.type = this.javaClass.name
    ex.module = this.javaClass.getPackage()?.name
    ex.value = this.message

    // TODO: Add stacktrace
    return ex
}
