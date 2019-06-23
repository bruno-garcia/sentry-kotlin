import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.31"
}

allprojects {
    repositories {
        jcenter()
    }
}

val javaVersion = JavaVersion.VERSION_1_8

subprojects {
    group = "io.sentry.sentry-kotlin"
    version = "1.0"
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions.jvmTarget = "$javaVersion"
        dependencies {
            testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
            testCompile("org.jetbrains.kotlin:kotlin-test-junit:1.3.31")
        }
    }

    tasks.withType<Test>() {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
            showExceptions = true
            showStandardStreams = true
        }
    }
}
