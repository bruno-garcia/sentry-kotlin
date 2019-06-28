package io.sentry

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

import kotlin.coroutines.*
import kotlin.system.measureTimeMillis

// import kotlin.system.*

class SentryHubTest {
    @Test
    fun `Scopes are separated in various `() {
        val time1 = measureTimeMillis {
            runBlocking {
                coroutineScope {
                    launch {
                        println("scope1 start")
                        delay(100)
                        println("scope1 end")
                    }
                    launch {
                        println("scope2 start")
                        delay(100)
                        println("scope2 end")
                    }
                }
            }
        }
        println("Test 1 time $time1")
    }
    @Test
    fun `test2`() {
        val time1 = measureTimeMillis {
            runBlocking {
                coroutineScope {
                    launch {
                        println("scope1 start")
                        delay(100)
                        println("scope1 end")
                    }
                }

                coroutineScope {
                    launch {
                        println("scope2 start")
                        delay(100)
                        println("scope2 end")
                    }
                }
            }
        }
        println("Test 2 time $time1")
    }
}