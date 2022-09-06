package com.r4g3baby.simplemotd.core.storage.provider.hikari

import com.r4g3baby.simplemotd.api.config.model.Storage
import com.r4g3baby.simplemotd.core.storage.classloader.IsolatedClassLoader
import com.zaxxer.hikari.HikariConfig
import java.net.InetAddress
import java.sql.PreparedStatement

class PostgreSQLProvider(
    classLoader: IsolatedClassLoader, settings: Storage
) : HikariStorageProvider(classLoader, settings) {
    override val createTableQuery: String = """
        CREATE TABLE IF NOT EXISTS ${tablePrefix}player_names
        (
            ip       INET        NOT NULL,
            username VARCHAR(16) NOT NULL,
            CONSTRAINT ${tablePrefix}player_names_pk
                PRIMARY KEY (ip)
        )
    """.trimIndent()

    override val getPlayerUsernameQuery: String =
        "SELECT username FROM ${tablePrefix}player_names WHERE ip = ?::inet LIMIT 1"

    override val setPlayerUsernameQuery: String =
        "INSERT INTO ${tablePrefix}player_names(ip, username) VALUES (?::inet, ?) ON CONFLICT (ip) DO UPDATE SET username=?"

    override fun configureHikari(hikariConfig: HikariConfig, settings: Storage) {
        hikariConfig.driverClassName = "org.postgresql.Driver"
        hikariConfig.jdbcUrl = "jdbc:postgresql://${settings.address}/${settings.database}"
    }

    override fun getPlayerUsernameParams(stmt: PreparedStatement, ip: InetAddress) {
        stmt.setString(1, ip.hostAddress)
    }

    override fun setPlayerUsernameParams(stmt: PreparedStatement, ip: InetAddress, username: String) {
        stmt.setString(1, ip.hostAddress)
        stmt.setString(2, username)
        stmt.setString(3, username)
    }
}