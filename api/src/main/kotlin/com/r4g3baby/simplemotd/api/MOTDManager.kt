package com.r4g3baby.simplemotd.api

import com.r4g3baby.simplemotd.api.config.Config
import com.r4g3baby.simplemotd.api.i18n.I18n
import com.r4g3baby.simplemotd.api.storage.Storage

interface MOTDManager {
    companion object {
        @JvmStatic
        var instance: MOTDManager? = null
            set(value) {
                check(field == null) { "Manager instance has already been set." }

                field = value
            }
    }

    val plugin: MOTDPlugin

    val config: Config

    val i18n: I18n

    val storage: Storage

    fun reloadConfig()
}