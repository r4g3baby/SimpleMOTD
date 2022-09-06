package com.r4g3baby.simplemotd.core.storage

import com.r4g3baby.simplemotd.api.MOTDManager
import com.r4g3baby.simplemotd.api.storage.Storage
import com.r4g3baby.simplemotd.api.storage.provider.Provider
import com.r4g3baby.simplemotd.core.storage.classloader.IsolatedClassLoader
import com.r4g3baby.simplemotd.core.storage.model.Driver
import com.r4g3baby.simplemotd.core.storage.provider.hikari.MariaDBProvider
import com.r4g3baby.simplemotd.core.storage.provider.hikari.MySQLProvider
import com.r4g3baby.simplemotd.core.storage.provider.hikari.PostgreSQLProvider
import com.r4g3baby.simplemotd.core.storage.provider.local.H2Provider
import com.r4g3baby.simplemotd.core.storage.provider.local.SQLiteProvider
import java.net.InetAddress
import java.net.URL
import java.nio.file.Path
import java.security.MessageDigest
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.writeBytes

class Storage(manager: MOTDManager) : Storage {
    private val sha256 = MessageDigest.getInstance("SHA-256")

    override var driver: com.r4g3baby.simplemotd.api.storage.model.Driver? = null
        private set

    private lateinit var provider: Provider

    init {
        driver = Driver.fromValue(manager.config.storage.driver)?.also { driver ->
            val dataFolder = manager.plugin.getDataFolder().toPath().toAbsolutePath()
            if (!dataFolder.exists()) dataFolder.createDirectories()

            val driversFolder = dataFolder.resolve("drivers")
            if (!driversFolder.exists()) driversFolder.createDirectories()

            val driverFile = driversFolder.resolve(driver.fileName)
            if (!driverFile.exists()) downloadDriver(driver, driverFile)

            val urls = arrayOf(driverFile.toUri().toURL())
            val classLoader = IsolatedClassLoader(urls)

            val driverName = driver.name.lowercase()
            val settings = manager.config.storage

            provider = when (driver) {
                Driver.H2 -> H2Provider(classLoader, dataFolder.resolve("data-$driverName"), settings)
                Driver.SQLite -> SQLiteProvider(classLoader, dataFolder.resolve("data-$driverName.db"), settings)
                Driver.PostgreSQL -> PostgreSQLProvider(classLoader, settings)
                Driver.MariaDB -> MariaDBProvider(classLoader, settings)
                Driver.MySQL -> MySQLProvider(classLoader, settings)
            }.apply { init() }
        }
    }

    fun shutdown() {
        if (this::provider.isInitialized) {
            provider.shutdown()
        }
    }

    override fun getPlayerUsername(ip: InetAddress): String? {
        if (this::provider.isInitialized) {
            return provider.getPlayerUsername(ip)
        }
        return null
    }

    override fun setPlayerUsername(ip: InetAddress, username: String) {
        if (this::provider.isInitialized) {
            provider.setPlayerUsername(ip, username)
        }
    }

    private fun downloadDriver(driver: Driver, driverFile: Path) {
        val connection = URL("https://repo1.maven.org/maven2/${driver.mavenPath}").openConnection()
        connection.getInputStream().use { inputStream ->
            val bytes = inputStream.readBytes()
            check(bytes.isNotEmpty()) { "Empty stream" }
            check(driver.validateHash(sha256.digest(bytes))) { "Invalid hash" }
            driverFile.writeBytes(bytes)
        }
    }
}