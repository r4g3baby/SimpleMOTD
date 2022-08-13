package com.r4g3baby.simplemotd

import com.r4g3baby.simplemotd.api.MOTDManager
import com.r4g3baby.simplemotd.api.MOTDPlugin
import com.r4g3baby.simplemotd.bukkit.BukkitManager
import com.r4g3baby.simplemotd.util.checkForUpdates
import org.bstats.bukkit.Metrics
import org.bukkit.plugin.java.JavaPlugin

class BukkitPlugin : JavaPlugin(), MOTDPlugin {
    private lateinit var bukkitManager: BukkitManager

    override fun onEnable() {
        bukkitManager = BukkitManager(this)
        MOTDManager.instance = bukkitManager

        Metrics(this, ProjectInfo.bStatsID)
        if (bukkitManager.config.checkForUpdates) {
            server.scheduler.runTaskAsynchronously(this) {
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
        if (this::bukkitManager.isInitialized) {
            bukkitManager.onDisable()
        }
    }

    override fun getManager(): MOTDManager {
        return bukkitManager
    }
}