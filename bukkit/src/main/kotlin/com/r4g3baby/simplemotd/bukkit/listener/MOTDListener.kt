package com.r4g3baby.simplemotd.bukkit.listener

import com.r4g3baby.simplemotd.BukkitPlugin
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.event.server.ServerListPingEvent

class MOTDListener(private val plugin: BukkitPlugin): Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onServerListPing(e: ServerListPingEvent) {
        val username = plugin.getManager().storage.getPlayerUsername(e.address)

        val text = if (username != null) {
            MiniMessage.miniMessage().deserialize("<red>Hello <username>!", Placeholder.unparsed("username", username))
        } else MiniMessage.miniMessage().deserialize("<red>Hello World!")

        e.motd = LegacyComponentSerializer.legacySection().serialize(text)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onAsyncPlayerPreLogin(e: PlayerLoginEvent) {
        if (e.player.name != null) {
            plugin.server.scheduler.runTaskAsynchronously(plugin) {
                plugin.getManager().storage.setPlayerUsername(e.address, e.player.name)
            }
        }
    }
}