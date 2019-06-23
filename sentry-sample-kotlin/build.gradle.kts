plugins {
    application
    kotlin("jvm")
}

apply(plugin = "org.jmailen.kotlinter")

application {
    mainClassName = "io.sentry.MainKt"
}

dependencies {
    implementation(project(":sentry"))
    implementation(kotlin("stdlib"))
    implementation(kotlin("stdlib-jdk8"))
}

