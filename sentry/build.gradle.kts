plugins {
    kotlin("jvm")
}

apply(plugin = "org.jmailen.kotlinter")

dependencies {
    api(project(":sentry-protocol"))
    implementation(kotlin("stdlib"))
    api(kotlin("stdlib-jdk8"))
}
