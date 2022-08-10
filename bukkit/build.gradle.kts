plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()

    maven(uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/"))
}

dependencies {
    api(project(":core"))

    compileOnly("org.bukkit:bukkit:1.8.8-R0.1-SNAPSHOT")

    implementation("org.bstats:bstats-bukkit:3.0.0")
}
