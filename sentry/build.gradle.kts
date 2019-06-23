plugins {
    kotlin("jvm")
}

apply(plugin = "org.jmailen.kotlinter")

dependencies {
    api(project(":sentry-protocol"))
    implementation(kotlin("stdlib"))
    implementation(kotlin("stdlib-jdk8"))
}
