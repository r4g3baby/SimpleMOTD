plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    api(project(":api"))

    api("net.swiftzer.semver:semver:1.2.0")
    api("net.kyori:adventure-text-minimessage:4.11.0")
    api("net.kyori:adventure-text-serializer-legacy:4.11.0")

    implementation("org.json:json:20220320")
    implementation("com.zaxxer:HikariCP:4.0.3")
    implementation("org.slf4j:slf4j-nop:1.7.36")
}
