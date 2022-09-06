package com.r4g3baby.simplemotd.bukkit

import com.r4g3baby.simplemotd.BukkitPlugin
import com.r4g3baby.simplemotd.bukkit.config.MainConfig
import com.r4g3baby.simplemotd.bukkit.listener.MOTDListener
import com.r4g3baby.simplemotd.core.BaseManager
import org.bukkit.configuration.file.YamlConfiguration

class BukkitManager(plugin: BukkitPlugin) : BaseManager<YamlConfiguration, MainConfig>(plugin) {
    init {
        loadManager(config = MainConfig(plugin))

        plugin.server.pluginManager.registerEvents(MOTDListener(plugin), plugin)
    }
}