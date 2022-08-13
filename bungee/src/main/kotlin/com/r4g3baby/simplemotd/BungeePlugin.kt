package com.r4g3baby.simplemotd

import com.r4g3baby.simplemotd.api.MOTDManager
import com.r4g3baby.simplemotd.api.MOTDPlugin
import com.r4g3baby.simplemotd.bungee.BungeeManager
import com.r4g3baby.simplemotd.util.checkForUpdates
import net.md_5.bungee.api.plugin.Plugin
import org.bstats.bungeecord.Metrics

class BungeePlugin : Plugin(), MOTDPlugin {
    private lateinit var bungeeManager: BungeeManager

    override fun onEnable() {
        bungeeManager = BungeeManager(this)
        MOTDManager.instance = bungeeManager

        Metrics(this, ProjectInfo.bStatsID)
        if (bungeeManager.config.checkForUpdates) {
            proxy.scheduler.runAsync(this) {
                checkForUpdates { hasUpdate, newVersion ->
                    if (hasUpdate) {
                        logger.warning("New version (v$newVersion) available, download at:")
                        logger.warning("https://www.spigotmc.org/resources/${ProjectInfo.spigotID}/")
                    }
                }
            }
        }
    }

    override fun onDisable() {
        if (this::bungeeManager.isInitialized) {
            bungeeManager.onDisable()
        }
    }

    override fun getManager(): MOTDManager {
        return bungeeManager
    }
}