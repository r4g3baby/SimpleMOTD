package com.r4g3baby.simplemotd.core.storage.provider

import com.r4g3baby.simplemotd.api.config.model.Storage
import com.r4g3baby.simplemotd.api.storage.provider.Provider
import java.net.InetAddress
import java.sql.Connection
import java.sql.PreparedStatement

abstract class SQLStorageProvider(protected val settings: Storage) : Provider {
    protected val tablePrefix: String = settings.tablePrefix

    protected abstract val createTableQuery: String
    protected abstract val getPlayerUsernameQuery: String
    protected abstract val setPlayerUsernameQuery: String

    protected abstract fun <R> withConnection(action: (Connection) -> R): R

    override fun init() {
        withConnection { conn ->
            conn.prepareStatement(createTableQuery).use { stmt ->
                stmt.execute()
            }
        }
    }

    protected abstract fun getPlayerUsernameParams(stmt: PreparedStatement, ip: InetAddress)
    override fun getPlayerUsername(ip: InetAddress): String? {
        return withConnection { conn ->
            conn.prepareStatement(getPlayerUsernameQuery).use { stmt ->
                getPlayerUsernameParams(stmt, ip)

                val result = stmt.executeQuery()
                if (result.next()) {
                    return@withConnection result.getString("username")
                }
                return@withConnection null
            }
        }
    }

    protected abstract fun setPlayerUsernameParams(stmt: PreparedStatement, ip: InetAddress, username: String)
    override fun setPlayerUsername(ip: InetAddress, username: String) {
        withConnection { conn ->
            conn.prepareStatement(setPlayerUsernameQuery).use { stmt ->
                setPlayerUsernameParams(stmt, ip, username)

                stmt.execute()
            }
        }
    }
}