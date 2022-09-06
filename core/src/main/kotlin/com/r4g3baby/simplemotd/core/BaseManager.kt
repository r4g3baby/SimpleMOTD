package com.r4g3baby.simplemotd.core

import com.r4g3baby.simplemotd.api.MOTDManager
import com.r4g3baby.simplemotd.api.MOTDPlugin
import com.r4g3baby.simplemotd.core.config.BaseConfig
import com.r4g3baby.simplemotd.core.i18n.I18n
import com.r4g3baby.simplemotd.core.storage.Storage

abstract class BaseManager<T, C : BaseConfig<T>>(override val plugin: MOTDPlugin) : MOTDManager {
    final override lateinit var config: C
        private set
    final override lateinit var i18n: I18n
        private set
    final override lateinit var storage: Storage
        private set

    protected fun loadManager(config: C) {
        this.config = config.apply { loadConfig() }
        this.i18n = I18n(plugin, config.language)
        this.storage = Storage(this)
    }

    fun onDisable() {
        if (this::storage.isInitialized) {
            storage.shutdown()
        }
    }

    override fun reloadConfig() {
        config.loadConfig()
        i18n.loadTranslations(config.language)
    }
}