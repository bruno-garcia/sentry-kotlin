import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.31"
}

repositories {
    mavenCentral()
//    jcenter()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<Wrapper> {
    gradleVersion = "5.0"
}

// config JVM target to 1.8 for kotlin compilation tasks
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
