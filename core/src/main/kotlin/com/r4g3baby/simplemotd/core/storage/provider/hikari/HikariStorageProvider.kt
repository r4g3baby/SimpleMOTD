package com.r4g3baby.simplemotd.core.storage.provider.hikari

import com.r4g3baby.simplemotd.ProjectInfo
import com.r4g3baby.simplemotd.api.config.model.Storage
import com.r4g3baby.simplemotd.core.storage.classloader.IsolatedClassLoader
import com.r4g3baby.simplemotd.core.storage.provider.SQLStorageProvider
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection

abstract class HikariStorageProvider(
    private val classLoader: IsolatedClassLoader, settings: Storage
) : SQLStorageProvider(settings) {
    protected abstract fun configureHikari(hikariConfig: HikariConfig, settings: Storage)

    private lateinit var dataSource: HikariDataSource
    override fun <R> withConnection(action: (Connection) -> R): R {
        return dataSource.connection.use(action)
    }

    override fun init() {
        val hikariConfig = HikariConfig().apply {
            poolName = ProjectInfo.name

            username = settings.username
            password = settings.password

            with(settings) {
                maximumPoolSize = pool.maximumPoolSize
                minimumIdle = pool.minimumIdle
                maxLifetime = pool.maxLifetime
                keepaliveTime = pool.keepaliveTime
                connectionTimeout = pool.connectionTimeout
            }

            initializationFailTimeout = -1
        }

        val oldClassLoader = Thread.currentThread().contextClassLoader
        Thread.currentThread().contextClassLoader = classLoader

        configureHikari(hikariConfig, settings)
        setProperties(hikariConfig, settings.pool.extraProperties.toMutableMap())

        dataSource = HikariDataSource(hikariConfig)

        Thread.currentThread().contextClassLoader = oldClassLoader

        super.init()
    }

    override fun shutdown() {
        dataSource.close()
    }

    protected open fun setProperties(hikariConfig: HikariConfig, properties: MutableMap<String, Any>) {
        properties.forEach { (name, value) ->
            hikariConfig.addDataSourceProperty(name, value)
        }
    }
}