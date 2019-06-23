plugins {
    kotlin("jvm")
}

dependencies {
    compile(project(":sentry-protocol"))
    compile(kotlin("stdlib"))
    implementation(kotlin("stdlib-jdk8"))
}
