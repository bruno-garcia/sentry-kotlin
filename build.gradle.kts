import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    kotlin("jvm") version "1.3.31"
}

allprojects {
    repositories {
        jcenter()
        mavenCentral()
    }
}

subprojects {
    group = "io.sentry.sentry-kotlin"
    version = "1.0"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    // or compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
