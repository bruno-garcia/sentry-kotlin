package io.sentry

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

internal class HttpTransport constructor(
    private val serializer: (SentryEvent) -> String,
    private val dsn: Dsn
) :
    Transport {

    private val client = OkHttpClient()
    private val JSON = MediaType.parse("application/json; charset=utf-8")

    override suspend fun captureEvent(event: SentryEvent): String {
        val auth = SentryHeaders.getSentryAuth(
            7,
            "sentry.kotlin",
            dsn.publicKey,
            dsn.secretKey
        )

        val json = serializer(event)

        val body = RequestBody.create(JSON, json)
        val request = Request.Builder()
            .url(dsn.sentryUrl)
            .header(auth.first, auth.second)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            val ret = response.body()!!.string()
            println("Sentry replied: $ret")
            return ret
        }

//        suspendCancellableCoroutine { continuation ->
//            enqueue(object : Callback {
//                override fun onResponse(call: Call, response: Response) {
//                    continuation.resume(response)
//                }
//
//                override fun onFailure(call: Call, e: IOException) {
//                    // Don't bother with resuming the continuation if it is already cancelled.
//                    if (continuation.isCancelled) return
//                    callStack?.initCause(e)
//                    continuation.resumeWithException(callStack ?: e)
//                }
//            })
//
//            continuation.invokeOnCancellation {
//                try {
//                    cancel()
//                } catch (ex: Throwable) {
//                    //Ignore cancel exception
//                }
//            }
//        }    }
    }

    override suspend fun flush() {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun close() {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }
}