plugins {
    kotlin("jvm")
}

apply(plugin = "org.jmailen.kotlinter")

dependencies {
    compile(project(":sentry-protocol"))
    compile(kotlin("stdlib"))
    implementation(kotlin("stdlib-jdk8"))
}
