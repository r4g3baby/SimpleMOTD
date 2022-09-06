plugins {
    id("com.github.johnrengelman.shadow")
    kotlin("jvm")
}

group = "com.r4g3baby"
version = "3.0.0-dev"

repositories {
    mavenCentral()
}

dependencies {
    api(project("bukkit"))
    api(project("bungee"))
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}

allprojects {
    afterEvaluate {
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
                filesMatching(listOf("**plugin.yml", "**bungee.yml", "**project.properties")) {
                    filter<org.apache.tools.ant.filters.ReplaceTokens>(
                        "tokens" to mapOf(
                            "name" to rootProject.name,
                            "version" to rootProject.version,
                            "description" to "A simple motd plugin for your minecraft server.",
                            "package" to "${rootProject.group}.${rootProject.name.toLowerCase()}",
                            "website" to "https://r4g3baby.com"
                        )
                    )
                }
            }
        }
    }
}

tasks {
    shadowJar {
        archiveFileName.set("${project.name}-${project.version}.jar")

        val lib = "${project.group}.${project.name.toLowerCase()}.lib"
        relocate("net.swiftzer.semver", "$lib.semver")
        relocate("net.kyori.adventure", "$lib.adventure")
        relocate("net.kyori.examination", "$lib.examination")
        relocate("org.json", "$lib.json")
        relocate("com.zaxxer.hikari", "$lib.hikari")
        relocate("org.slf4j", "$lib.slf4j")
        relocate("org.bstats", "$lib.bstats")
        relocate("org.jetbrains", "$lib.jetbrains")
        relocate("org.intellij", "$lib.intellij")
        relocate("kotlin", "$lib.kotlin")

        from(file("LICENSE"))

        dependencies {
            exclude("META-INF/maven/**")
            exclude("META-INF/services/**")
            exclude("META-INF/versions/**")
            exclude("META-INF/**.kotlin_module")
        }

        minimize()
    }
}
