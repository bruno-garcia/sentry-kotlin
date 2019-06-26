package io.sentry

import java.net.URI

internal class Dsn(dsn: String) {
    private val _dsn: InternalDsn

    val projectId: String get() = _dsn.projectId
    val secretKey: String? get() = _dsn.secretKey
    val publicKey: String get() = _dsn.publicKey
    val sentryUrl: String get() = _dsn.sentryUrl

    init {
        _dsn = parse(dsn)
    }

    companion object {
        private fun fromURI(dsn: URI): InternalDsn {
            if (dsn.path == "/") {
                throw Exception("Missing Sentry project in DSN.") // Of a better type
            }

            if (dsn.userInfo == null) {
                throw Exception("Invalid DSN, missing project key")
            }

            val keys = dsn.userInfo.split(":")
            val publicKey = keys[0]
            val secretKey = if (keys.size > 1) keys[1] else ""

            if (publicKey.isBlank()) {
                throw Exception("Invalid Dns, blank project key")
            }

            val scheme = dsn.scheme // http | https
            val host = dsn.host
            val port: Int = dsn.port
            val pathSegments = dsn.path.trim('/').split("/")
            val projectId = pathSegments.last()

            val path = if (pathSegments.size > 1)
                pathSegments.dropLast(1).joinToString(prefix = "/", separator = "/") else ""

            val endpointPath = "$path/api/$projectId/store/"

            if (projectId.isBlank()) {
                throw Exception("Invalid DSN, missing project Id")
            }

            val sentryUrl = URI(scheme,
                null,
                host,
                port,
                endpointPath,
                null,
                null).toString()

            // https://github.com/getsentry/sentry-dotnet-protocol/blob/master/src/Sentry.Protocol/Dsn.cs
            // https://github.com/getsentry/rust-sentry-types/blob/master/src/dsn.rs
            return InternalDsn(
                projectId = projectId,
                path = endpointPath,
                secretKey = secretKey,
                publicKey = publicKey,
                sentryUrl = sentryUrl
            )
        }

        private fun parse(dsn: String): InternalDsn {
            val uri = URI(dsn)
            return fromURI(uri)
        }
    }

    private data class InternalDsn(
        val projectId: String,
        val path: String,
        val secretKey: String?,
        val publicKey: String,
        val sentryUrl: String
    )
}