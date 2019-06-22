plugins {
    application
    java
}

application {
    mainClassName = "io.sentry.Main"
}

dependencies {
    compile(project(":sentry"))
}

