package com.r4g3baby.simplemotd.bungee.config

import com.r4g3baby.simplemotd.BungeePlugin
import com.r4g3baby.simplemotd.core.config.ConfigFile
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.Reader

class Config(plugin: BungeePlugin) : ConfigFile<Configuration>(plugin.dataFolder, "config") {
    private val yamlProvider = ConfigurationProvider.getProvider(YamlConfiguration::class.java)

    private var version: Int = 0
    private var language: String = "en"
    private var checkForUpdates: Boolean = true

    override fun parseConfigFile(reader: Reader?): Configuration {
        return if (reader != null) {
            yamlProvider.load(reader)
        } else Configuration()
    }

    override fun loadVariables(config: Configuration) {
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