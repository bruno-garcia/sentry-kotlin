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
            testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
            testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.3.31")
        }
        dependsOn(tasks.named("formatKotlin"))
    }
}

buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("org.jmailen.gradle:kotlinter-gradle:1.26.0")
    }
}