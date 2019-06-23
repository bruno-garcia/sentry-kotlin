plugins {
    application
    kotlin("jvm")
}

apply(plugin = "org.jmailen.kotlinter")

application {
    mainClassName = "io.sentry.MainKt"
}

dependencies {
    compile(project(":sentry"))
    compile(kotlin("stdlib"))
    implementation(kotlin("stdlib-jdk8"))
}

