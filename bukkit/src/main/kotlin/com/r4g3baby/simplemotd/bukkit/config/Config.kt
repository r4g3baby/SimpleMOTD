package com.r4g3baby.simplemotd.bukkit.config

import com.r4g3baby.simplemotd.BukkitPlugin
import com.r4g3baby.simplemotd.core.config.ConfigFile
import org.bukkit.configuration.file.YamlConfiguration
import java.io.Reader

class Config(plugin: BukkitPlugin) : ConfigFile<YamlConfiguration>(plugin.dataFolder, "config") {
    private var version: Int = 0
    private var language: String = "en"
    private var checkForUpdates: Boolean = true

    override fun parseConfigFile(reader: Reader?): YamlConfiguration {
        return if (reader != null) {
            YamlConfiguration.loadConfiguration(reader)
        } else YamlConfiguration()
    }

    override fun loadVariables(config: YamlConfiguration) {
        version = config.getInt("version", version)
        language = config.getString("language", language)
        checkForUpdates = config.getBoolean("checkForUpdates", checkForUpdates)
    }

    override fun getVersion(): Int {
        return version
    }

    override fun getLanguage(): String {
        return language
    }

    override fun checkForUpdates(): Boolean {
        return checkForUpdates
    }
}