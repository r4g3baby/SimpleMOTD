package com.r4g3baby.simplemotd.bungee

import com.r4g3baby.simplemotd.BungeePlugin
import com.r4g3baby.simplemotd.bungee.config.MainConfig
import com.r4g3baby.simplemotd.core.BaseManager
import com.r4g3baby.simplemotd.util.translateAlternateColorCodes
import net.md_5.bungee.api.event.ProxyPingEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.event.EventHandler

class BungeeManager(plugin: BungeePlugin) : BaseManager<Configuration, MainConfig>(plugin) {
    init {
        loadManager(config = MainConfig(plugin))

        plugin.proxy.pluginManager.registerListener(plugin, object : Listener {
            @EventHandler
            fun onProxyPing(e: ProxyPingEvent) {
                e.response.description = translateAlternateColorCodes("&cHello World!")
            }
        })
    }
}