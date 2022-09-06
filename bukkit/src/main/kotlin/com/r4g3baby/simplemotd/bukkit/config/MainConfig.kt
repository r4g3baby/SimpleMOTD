package com.r4g3baby.simplemotd.bukkit.config

import com.r4g3baby.simplemotd.BukkitPlugin
import com.r4g3baby.simplemotd.api.config.model.Storage
import com.r4g3baby.simplemotd.core.config.BaseConfig
import org.bukkit.configuration.ConfigurationSection
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
        storage = config.getConfigurationSection("storage").asStorage()
    }

    private fun ConfigurationSection.asStorage(): Storage {
        return object : Storage {
            override val driver = getString("driver", storage.driver)
            override val tablePrefix = getString("tablePrefix", storage.tablePrefix)
            override val address = getString("address", storage.address)
            override val database = getString("database", storage.database)
            override val username = getString("username", storage.username)
            override val password = getString("password", storage.password)
            override val pool = getConfigurationSection("pool").asStoragePool()
        }
    }

    private fun ConfigurationSection.asStoragePool(): Storage.Pool {
        return object : Storage.Pool {
            override val maximumPoolSize = getInt("maximumPoolSize", storage.pool.maximumPoolSize)
            override val minimumIdle = getInt("minimumIdle", storage.pool.minimumIdle)
            override val maxLifetime = getLong("maxLifetime", storage.pool.maxLifetime)
            override val keepaliveTime = getLong("keepaliveTime", storage.pool.keepaliveTime)
            override val connectionTimeout = getLong("connectionTimeout", storage.pool.connectionTimeout)
            override val extraProperties = getConfigurationSection("extraProperties").getValues(false) ?: emptyMap()
        }
    }
}