pluginManagement {
    plugins {
        id("com.github.johnrengelman.shadow") version "7.1.2"
        kotlin("jvm") version "1.7.10"
    }
}

rootProject.name = "SimpleMOTD"

include("api")
include("core")
include("bukkit")
include("bungee")
