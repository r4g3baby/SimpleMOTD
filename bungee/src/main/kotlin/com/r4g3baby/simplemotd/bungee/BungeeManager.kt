package com.r4g3baby.simplemotd.bungee

import com.r4g3baby.simplemotd.BungeePlugin
import com.r4g3baby.simplemotd.bungee.config.MainConfig
import com.r4g3baby.simplemotd.bungee.listener.MOTDListener
import com.r4g3baby.simplemotd.core.BaseManager
import net.md_5.bungee.config.Configuration

class BungeeManager(plugin: BungeePlugin) : BaseManager<Configuration, MainConfig>(plugin) {
    init {
        loadManager(config = MainConfig(plugin))

        plugin.proxy.pluginManager.registerListener(plugin, MOTDListener(plugin))
    }
}