import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "$kotlin_version"
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.31 "
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

buildscript {
    apply from: "./gradle/versions.gradle"

    repositories {
        google()
        jcenter()
    }

}
