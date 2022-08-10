package com.r4g3baby.simplemotd.bukkit

import com.r4g3baby.simplemotd.BukkitPlugin
import com.r4g3baby.simplemotd.bukkit.config.Config
import com.r4g3baby.simplemotd.core.Manager
import com.r4g3baby.simplemotd.util.translateAlternateColorCodes
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent

class BukkitManager(plugin: BukkitPlugin) : Manager<YamlConfiguration, Config>(plugin) {
    init {
        loadManager(config = Config(plugin))

        plugin.server.pluginManager.registerEvents(object : Listener {
            @EventHandler
            fun onServerListPing(e: ServerListPingEvent) {
                e.motd = translateAlternateColorCodes("&cHello World!")
            }
        }, plugin)
    }
}