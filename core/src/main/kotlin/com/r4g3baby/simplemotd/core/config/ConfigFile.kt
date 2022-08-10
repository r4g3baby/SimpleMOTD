package com.r4g3baby.simplemotd.core.config

import com.r4g3baby.simplemotd.api.config.Config
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.Reader

abstract class ConfigFile<T>(parent: File, name: String) : File(parent, "$name.yml"), Config {
    protected abstract fun parseConfigFile(reader: Reader?): T
    protected abstract fun loadVariables(config: T)

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