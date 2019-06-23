package io.sentry

import java.net.URI

class Dsn {
    private val _dsn: InternalDsn

    val projectId: String get() = _dsn.projectId
    val path: String get() = _dsn.path
    val secretKey: String? get() = _dsn.secretKey
    val publicKey: String get() = _dsn.publicKey
    val sentryUrl: String get() = _dsn.sentryUrl

    constructor(dsn: String) {
        _dsn = parse(dsn)
    }

    companion object {
        private fun fromURI(dsn: URI): InternalDsn {
            if (dsn.path == "/") {
                throw Exception("Missing Sentry project in DSN.") // Of a better type
            }
            // https://github.com/getsentry/sentry-dotnet-protocol/blob/master/src/Sentry.Protocol/Dsn.cs
            // https://github.com/getsentry/rust-sentry-types/blob/master/src/dsn.rs
            return InternalDsn("TODO", "TODO", "TODO", "TODO", "TODO")
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