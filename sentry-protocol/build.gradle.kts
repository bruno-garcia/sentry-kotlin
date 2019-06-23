plugins {
    kotlin("jvm")
}

apply(plugin = "org.jmailen.kotlinter")

dependencies {
    compile(kotlin("stdlib"))
    implementation(kotlin("stdlib-jdk8"))
}
