package com.r4g3baby.simplemotd.core.config

import com.r4g3baby.simplemotd.api.config.Config
import com.r4g3baby.simplemotd.api.config.model.Storage
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.Reader

abstract class BaseConfig<T>(parent: File, name: String) : File(parent, "$name.yml"), Config {
    protected abstract fun parseConfigFile(reader: Reader?): T
    protected abstract fun loadVariables(config: T)

    override var version: Int = 0
    override var language: String = "en"
    override var checkForUpdates: Boolean = true
    override var storage: Storage = object : Storage {
        override val driver: String = "H2"
        override val tablePrefix: String = "simplemotd_"
        override val address: String = "127.0.0.1:3306"
        override val database: String = "minecraft"
        override val username: String = "simplemotd"
        override val password: String = "|D/-\\55\\^/0|2|)"
        override val pool: Storage.Pool = object : Storage.Pool {
            override val maximumPoolSize: Int = 8
            override val minimumIdle: Int = 8
            override val maxLifetime: Long = 1800000
            override val keepaliveTime: Long = 0
            override val connectionTimeout: Long = 5000
            override val extraProperties: Map<String, Any> = mapOf(
                "useUnicode" to true,
                "characterEncoding" to "utf8"
            )
        }
    }

    open fun loadConfig() {
        if (!this.exists()) {
            if (!this.parentFile.exists()) this.parentFile.mkdirs()

            javaClass.classLoader.getResourceAsStream(this.name)?.use { stream ->
                this.writeBytes(stream.readBytes())
            }
        }

        val config = if (this.exists()) {
            InputStreamReader(FileInputStream(this), Charsets.UTF_8).use { reader ->
                parseConfigFile(reader)
            }
        } else parseConfigFile(null)

        loadVariables(config)
    }
}