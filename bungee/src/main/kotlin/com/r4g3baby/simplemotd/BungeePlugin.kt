package com.r4g3baby.simplemotd

import com.r4g3baby.simplemotd.api.MOTDManager
import com.r4g3baby.simplemotd.bungee.BungeeManager
import com.r4g3baby.simplemotd.core.MOTDPlugin
import com.r4g3baby.simplemotd.util.checkForUpdates
import net.md_5.bungee.api.plugin.Plugin
import org.bstats.bungeecord.Metrics

class BungeePlugin : Plugin(), MOTDPlugin {
    private var manager: BungeeManager? = null

    override fun onEnable() {
        manager = BungeeManager(this)
        MOTDManager.instance = manager

        Metrics(this, ProjectInfo.bStatsID)
        if (manager?.getConfig()?.checkForUpdates() == true) {
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
        manager?.onDisable()
    }
}