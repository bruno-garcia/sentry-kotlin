package main.kotlin.io.sentry

import org.junit.Assert.*

@RunWith(Arquillian::class)
class SentryEventTest {

    @org.junit.Before
    fun setUp() {
    }

    @org.junit.After
    fun tearDown() {
    }

    @org.junit.Test
    fun getName() {
    }

    @org.junit.Test
    fun setName() {
    }

    @org.junit.Test
    fun getAge() {
    }

    @org.junit.Test
    fun setAge() {
    }

    companion object {
        @Deployment
        fun createDeployment(): JavaArchive {
            return ShrinkWrap.create(JavaArchive::class.java)
                .addClass(io.sentry.SentryEvent::class.java)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
        }
    }
}
