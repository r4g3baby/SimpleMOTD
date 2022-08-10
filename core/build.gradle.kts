plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    api(project(":api"))
    api("net.swiftzer.semver:semver:1.2.0")

    implementation("org.json:json:20220320")
}
