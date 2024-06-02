plugins {
    kotlin("jvm") version "2.0.0"
}

group = "org.graphite"
version = "0.1.0"

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}