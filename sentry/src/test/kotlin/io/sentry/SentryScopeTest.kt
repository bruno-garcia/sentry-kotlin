package io.sentry

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

import kotlin.coroutines.*
import kotlin.system.measureTimeMillis

// import kotlin.system.*

class SentryScopeTest {
    @Test
    fun `test1`() {
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

    @Test
    fun cloning() {
        val x = mutableListOf("a", "c", "d")

        val y = x.toMutableList()

        y.add("m")
        x.add("n")
        println("x is $x")
        println("y is $y")
    }
}