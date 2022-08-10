package com.r4g3baby.simplemotd.core

import com.r4g3baby.simplemotd.api.MOTDManager
import com.r4g3baby.simplemotd.api.config.Config
import com.r4g3baby.simplemotd.core.config.ConfigFile
import com.r4g3baby.simplemotd.core.i18n.I18n

abstract class Manager<T, C : ConfigFile<T>>(private val plugin: MOTDPlugin) : MOTDManager {
    private lateinit var config: C
    private lateinit var i18n: I18n

    protected fun loadManager(config: C) {
        this.config = config.apply { loadConfig() }
        this.i18n = I18n(config.getLanguage(), plugin)
    }

    fun onDisable() {

    }

    override fun getConfig(): Config {
        return config
    }

    override fun getI18n(): I18n {
        return i18n
    }

    override fun reloadConfig() {
        config.loadConfig()
        i18n.loadTranslations(config.getLanguage())
    }
}