package com.r4g3baby.simplemotd.util

import com.r4g3baby.simplemotd.ProjectInfo
import net.swiftzer.semver.SemVer
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

private const val spigotUrl =
    "https://api.spigotmc.org/simple/0.2/index.php?action=getResource&id=${ProjectInfo.spigotID}"

fun checkForUpdates(consumer: (Boolean, SemVer) -> Unit) {
    val conn = URL(spigotUrl).openConnection() as HttpURLConnection
    try {
        conn.requestMethod = "GET"
        conn.useCaches = false
        conn.connectTimeout = 3000
        conn.readTimeout = 3000
        conn.setRequestProperty("Accept", "application/json")
        conn.setRequestProperty("User-Agent", "${ProjectInfo.name}/${ProjectInfo.version}")

        BufferedReader(InputStreamReader(conn.inputStream)).use { reader ->
            val jsonVersion = JSONObject(reader)["current_version"]
            if (jsonVersion != null && jsonVersion is String) {
                val latestVersion = SemVer.parse(jsonVersion)

                if (ProjectInfo.version < latestVersion) {
                    consumer(true, latestVersion)
                } else consumer(false, latestVersion)
            } else consumer(false, ProjectInfo.version)
        }
    } catch (_: Exception) {
        consumer(false, ProjectInfo.version)
    } finally {
        conn.disconnect()
    }
}
