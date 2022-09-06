package com.r4g3baby.simplemotd.core.storage.provider.local

import com.r4g3baby.simplemotd.api.config.model.Storage
import com.r4g3baby.simplemotd.core.storage.classloader.IsolatedClassLoader
import java.lang.reflect.Constructor
import java.net.InetAddress
import java.nio.file.Path
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import java.util.*

class SQLiteProvider(
    classLoader: IsolatedClassLoader, file: Path, settings: Storage
) : LocalStorageProvider(file, settings) {
    override val createTableQuery: String = """
        CREATE TABLE IF NOT EXISTS ${tablePrefix}player_names
        (
            ip       BINARY(4)   NOT NULL,
            username VARCHAR(16) NOT NULL,
            CONSTRAINT ${tablePrefix}player_names_pk
                PRIMARY KEY (ip)
        )
    """.trimIndent()

    override val getPlayerUsernameQuery: String =
        "SELECT username FROM ${tablePrefix}player_names WHERE ip = ? LIMIT 1"

    override val setPlayerUsernameQuery: String =
        "INSERT INTO ${tablePrefix}player_names(ip, username) VALUES (?, ?) ON CONFLICT DO UPDATE SET username=?"

    private val connectionConstructor: Constructor<*>

    init {
        try {
            val connectionClass = classLoader.loadClass("org.sqlite.jdbc4.JDBC4Connection")
            connectionConstructor = connectionClass.getConstructor(
                String::class.java, String::class.java, Properties::class.java
            )
        } catch (ex: ReflectiveOperationException) {
            throw RuntimeException(ex)
        }
    }

    override fun createConnection(): Connection {
        try {
            return connectionConstructor.newInstance("jdbc:sqlite:$file", file.toString(), Properties()) as Connection
        } catch (ex: ReflectiveOperationException) {
            if (ex.cause is SQLException) {
                throw ex.cause as SQLException
            }
            throw RuntimeException(ex)
        }
    }

    override fun getPlayerUsernameParams(stmt: PreparedStatement, ip: InetAddress) {
        stmt.setBytes(1, ip.address)
    }

    override fun setPlayerUsernameParams(stmt: PreparedStatement, ip: InetAddress, username: String) {
        stmt.setBytes(1, ip.address)
        stmt.setString(2, username)
        stmt.setString(3, username)
    }
}