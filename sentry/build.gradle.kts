plugins {
    kotlin("jvm")
}

apply(plugin = "org.jmailen.kotlinter")

dependencies {
    api(project(":sentry-protocol"))
    implementation(kotlin("stdlib"))
    api(kotlin("stdlib-jdk8"))
    implementation("com.google.code.gson:gson:2.8.5")
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib:1.2.21")
}

