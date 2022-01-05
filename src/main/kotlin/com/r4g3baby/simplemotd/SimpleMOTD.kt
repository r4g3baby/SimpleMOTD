package com.r4g3baby.simplemotd

import com.r4g3baby.simplemotd.utils.updater.UpdateChecker
import org.bstats.bukkit.Metrics
import org.bukkit.plugin.java.JavaPlugin

class SimpleMOTD : JavaPlugin() {
    private val pluginId = 23243

    override fun onEnable() {
        Metrics(this, 13853)

        UpdateChecker(this, pluginId) { hasUpdate, newVersion ->
            if (hasUpdate) {
                logger.warning("New version (v$newVersion) available, download at:")
                logger.warning("https://www.spigotmc.org/resources/$pluginId/")
            }
        }
    }
}