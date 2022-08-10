package com.r4g3baby.simplemotd

import net.swiftzer.semver.SemVer
import java.util.*

object ProjectInfo {
    const val spigotID: Int = 2581
    const val bStatsID: Int = 13853

    var name: String = "NaN"
        private set
    var version: SemVer = SemVer()
        private set

    init {
        javaClass.classLoader.getResource("project.properties")?.let { projectFile ->
            val properties = Properties().apply {
                load(projectFile.openStream())
            }

            name = properties.getProperty("name", name)
            version = SemVer.parse(properties.getProperty("version", version.toString()))
        }
    }
}