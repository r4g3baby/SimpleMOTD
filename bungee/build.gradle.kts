plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()

    maven(uri("https://oss.sonatype.org/content/repositories/snapshots"))
}

dependencies {
    api(project(":core"))

    compileOnly("net.md-5:bungeecord-api:1.19-R0.1-SNAPSHOT")

    implementation("org.bstats:bstats-bungeecord:3.0.0")
}
