package com.r4g3baby.simplemotd.bungee.config

import com.r4g3baby.simplemotd.BungeePlugin
import com.r4g3baby.simplemotd.api.config.model.Storage
import com.r4g3baby.simplemotd.core.config.BaseConfig
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.Reader

class MainConfig(plugin: BungeePlugin) : BaseConfig<Configuration>(plugin.dataFolder, "config") {
    private val yamlProvider = ConfigurationProvider.getProvider(YamlConfiguration::class.java)

    override fun parseConfigFile(reader: Reader?): Configuration {
        return if (reader != null) {
            yamlProvider.load(reader)
        } else Configuration()
    }

    override fun loadVariables(config: Configuration) {
        version = config.getInt("version", version)
        language = config.getString("language", language)
        checkForUpdates = config.getBoolean("checkForUpdates", checkForUpdates)
        storage = config.getSection("storage").asStorage()
    }

    private fun Configuration.asStorage(): Storage {
        return object : Storage {
            override val driver = getString("driver", storage.driver)
            override val tablePrefix = getString("tablePrefix", storage.tablePrefix)
            override val address = getString("address", storage.address)
            override val database = getString("database", storage.database)
            override val username = getString("username", storage.username)
            override val password = getString("password", storage.password)
            override val pool = getSection("pool").asStoragePool()
        }
    }

    private fun Configuration.asStoragePool(): Storage.Pool {
        nameWithoutExtension
        return object : Storage.Pool {
            override val maximumPoolSize = getInt("maximumPoolSize", storage.pool.maximumPoolSize)
            override val minimumIdle = getInt("minimumIdle", storage.pool.minimumIdle)
            override val maxLifetime = getLong("maxLifetime", storage.pool.maxLifetime)
            override val keepaliveTime = getLong("keepaliveTime", storage.pool.keepaliveTime)
            override val connectionTimeout = getLong("connectionTimeout", storage.pool.connectionTimeout)
            override val extraProperties: Map<String, Any> = emptyMap() // todo: finish this
        }
    }
}