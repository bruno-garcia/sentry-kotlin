plugins {
    application
    kotlin("jvm")
}


application {
    mainClassName = "io.sentry.MainKt"
}

dependencies {
    compile(project(":sentry"))
    compile(kotlin("stdlib"))
    implementation(kotlin("stdlib-jdk8"))
}

