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
                    "description" to "A simple motd plugin for your minecraft server.",
                    "package" to "${project.group}.${project.name.toLowerCase()}",
                    "website" to "https://r4g3baby.com"
                )
            )
        }
    }

    shadowJar {
        archiveFileName.set("${project.name}-${project.version}.jar")

        val libs = "${project.group}.${project.name.toLowerCase()}.libs"
        relocate("net.swiftzer.semver", "$libs.semver")
        relocate("org.bstats", "$libs.bstats")
        relocate("org.jetbrains", "$libs.jetbrains")
        relocate("org.intellij", "$libs.intellij")
        relocate("kotlin", "$libs.kotlin")

        dependencies {
            exclude("META-INF/maven/**")
            exclude("META-INF/versions/**")
            exclude("META-INF/**.kotlin_module")
        }

        minimize()
    }
}