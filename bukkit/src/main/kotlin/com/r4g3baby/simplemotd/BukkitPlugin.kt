package com.r4g3baby.simplemotd

import com.r4g3baby.simplemotd.api.MOTDManager
import com.r4g3baby.simplemotd.bukkit.BukkitManager
import com.r4g3baby.simplemotd.core.MOTDPlugin
import com.r4g3baby.simplemotd.util.checkForUpdates
import org.bstats.bukkit.Metrics
import org.bukkit.plugin.java.JavaPlugin

class BukkitPlugin : JavaPlugin(), MOTDPlugin {
    private var manager: BukkitManager? = null

    override fun onEnable() {
        manager = BukkitManager(this)
        MOTDManager.instance = manager

        Metrics(this, ProjectInfo.bStatsID)
        if (manager?.getConfig()?.checkForUpdates() == true) {
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
        manager?.onDisable()
    }
}