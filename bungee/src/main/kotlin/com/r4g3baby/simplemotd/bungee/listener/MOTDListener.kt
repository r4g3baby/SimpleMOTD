package com.r4g3baby.simplemotd.bungee.listener

import com.r4g3baby.simplemotd.BungeePlugin
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.md_5.bungee.api.event.LoginEvent
import net.md_5.bungee.api.event.ProxyPingEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import net.md_5.bungee.event.EventPriority
import java.net.InetSocketAddress

class MOTDListener(private val plugin: BungeePlugin) : Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onProxyPing(e: ProxyPingEvent) {
        e.registerIntent(plugin)

        plugin.proxy.scheduler.runAsync(plugin) {
            try {
                var username: String? = null

                val socketAddress = e.connection.socketAddress
                if (socketAddress is InetSocketAddress) {
                    username = plugin.getManager().storage.getPlayerUsername(socketAddress.address)
                }

                val text = if (username != null) {
                    MiniMessage.miniMessage()
                        .deserialize("<red>Hello <username>!", Placeholder.unparsed("username", username))
                } else MiniMessage.miniMessage().deserialize("<red>Hello World!")

                e.response.description = LegacyComponentSerializer.legacySection().serialize(text)
            } finally {
                e.completeIntent(plugin)
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerHandshake(e: LoginEvent) {
        if (e.isCancelled) return

        val socketAddress = e.connection.socketAddress
        if (e.connection.name != null && socketAddress is InetSocketAddress) {
            plugin.proxy.scheduler.runAsync(plugin) {
                plugin.getManager().storage.setPlayerUsername(socketAddress.address, e.connection.name)
            }
        }
    }
}