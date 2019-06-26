package io.sentry

import io.kotlintest.shouldBe
import io.kotlintest.tables.forAll
import io.kotlintest.tables.headers
import io.kotlintest.tables.row
import io.kotlintest.tables.table
import kotlin.test.Test

class DsnTest{
    @Test
    fun `should capture the dsn keys`(){
        table(
            headers("dsnStr", "publicKey", "secretKey"),
            row("https://1234@sentry.io/304324", "1234", ""),
            row("https://1234:5678@sentry.io/304324", "1234", "5678")

        ).forAll { dsnStr, publicKey, secretKey ->
            val dsn = Dsn(dsnStr)
            dsn.publicKey shouldBe publicKey
            dsn.secretKey shouldBe secretKey
        }
    }
    @Test
    fun `should create the proper store url`(){
        table(
            headers("dsnStr", "url"),
            row("https://1234@sentry.io/333/", "https://sentry.io/api/333/store/"),
            row("https://1234@someHost.com/a/b/333", "https://someHost.com/a/b/api/333/store/"),
            row("http://1234@sentry.io/333/", "http://sentry.io/api/333/store/")
        ).forAll{dsnStr , url->
            val dsn = Dsn(dsnStr)
            dsn.sentryUrl shouldBe url
        }
    }
    @Test
    fun `should extract the projectId from the dsn`(){
        table(
            headers("dsnStr", "projectId"),
            row("https://1234@sentry.io/333/", "333"),
            row("https://1234@someHost.com/a/b/333", "333")
        ).forAll{dsnStr , projectId->
            val dsn = Dsn(dsnStr)
            dsn.projectId shouldBe projectId
        }
    }

}