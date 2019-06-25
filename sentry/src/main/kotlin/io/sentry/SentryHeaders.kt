package io.sentry

import java.time.Instant
import java.time.ZoneOffset

internal class SentryHeaders {

    companion object {
        val SENTRY_ERROR_HEADER: String = "X-Sentry-Error"
        val SENTRY_AUTH_HEADER: String = "X-Sentry-Auth"

        fun getSentryAuth(
            sentryVersion: Int,
            clientVersion: String,
            publicKey: String,
            secretKey: String?
        ): Pair<String, String> {
            val baseAuthHeader = "Sentry sentry_version=$sentryVersion," +
                    "sentry_client=$clientVersion," +
                    "sentry_key=$publicKey," +
                    if (secretKey != null) "sentry_secret=$secretKey," else null +
                            "sentry_timestamp=" + Instant.now().atZone(ZoneOffset.UTC).toEpochSecond()

            return Pair(SENTRY_AUTH_HEADER, baseAuthHeader)
        }
    }
}
