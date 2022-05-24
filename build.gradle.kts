plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    kotlin("jvm") version "1.6.21"
}

group = "com.r4g3baby"
version = "3.0.0-dev"

repositories {
    mavenCentral()

    maven(uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/"))
}

dependencies {
    compileOnly("org.bukkit:bukkit:1.8.8-R0.1-SNAPSHOT")

    implementation("net.swiftzer.semver:semver:1.2.0")
    implementation("org.bstats:bstats-bukkit:3.0.0")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    processResources {
        filteringCharset = "UTF-8"
        filesMatching("**plugin.yml") {
            filter<org.apache.tools.ant.filters.ReplaceTokens>(
                "tokens" to mapOf(
                    "name" to project.name,
                    "version" to project.version,
                    "description" to "A simple motd plugin for your server.",
                    "package" to "${project.group}.${project.name.toLowerCase()}",
                    "website" to "https://r4g3baby.com"
                )
            )
        }
    }

    shadowJar {
        archiveFileName.set("${project.name}-${project.version}.jar")

        val shaded = "${project.group}.${project.name.toLowerCase()}.shaded"
        relocate("net.swiftzer.semver", "$shaded.semver")
        relocate("org.bstats", "$shaded.bstats")
        relocate("org.jetbrains", "$shaded.jetbrains")
        relocate("org.intellij", "$shaded.intellij")
        relocate("kotlin", "$shaded.kotlin")
    }
}