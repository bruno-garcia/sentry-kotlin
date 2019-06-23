plugins {
    application
    java
}

application {
    mainClassName = "io.sentry.Main"
}

dependencies {
    implementation(project(":sentry"))
}

