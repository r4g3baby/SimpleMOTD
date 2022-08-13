package com.r4g3baby.simplemotd.bukkit.config

import com.r4g3baby.simplemotd.BukkitPlugin
import com.r4g3baby.simplemotd.core.config.BaseConfig
import org.bukkit.configuration.file.YamlConfiguration
import java.io.Reader

class MainConfig(plugin: BukkitPlugin) : BaseConfig<YamlConfiguration>(plugin.dataFolder, "config") {
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
}