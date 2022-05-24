package com.r4g3baby.simplemotd.utils.updater

import net.swiftzer.semver.SemVer
import org.bukkit.plugin.Plugin
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class UpdateChecker(plugin: Plugin, pluginID: Int, consumer: (Boolean, String) -> Unit) {
    private val _spigotApi = "https://api.spigotmc.org/simple/0.2/index.php?action=getResource&id=$pluginID"

    init {
        plugin.server.scheduler.runTaskAsynchronously(plugin) {
            val currentVersion = SemVer.parse(plugin.description.version)
            val conn = URL(_spigotApi).openConnection() as HttpURLConnection
            try {
                conn.requestMethod = "GET"
                conn.useCaches = false
                conn.connectTimeout = 3000
                conn.readTimeout = 3000
                conn.setRequestProperty("Accept", "application/json")
                conn.setRequestProperty("User-Agent", "${plugin.name}/${plugin.description.version}")

                BufferedReader(InputStreamReader(conn.inputStream)).use { reader ->
                    val jsonObject = JSONParser().parse(reader) as JSONObject
                    val latestVersion = SemVer.parse(jsonObject["current_version"] as String)

                    if (currentVersion < latestVersion) {
                        consumer(true, latestVersion.toString())
                    } else consumer(false, latestVersion.toString())
                }
            } catch (_: Exception) {
                consumer(false, currentVersion.toString())
            } finally {
                conn.disconnect()
            }
        }
    }
}