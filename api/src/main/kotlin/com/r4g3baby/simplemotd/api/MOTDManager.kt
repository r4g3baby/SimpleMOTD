package com.r4g3baby.simplemotd.api

import com.r4g3baby.simplemotd.api.config.Config
import com.r4g3baby.simplemotd.api.i18n.I18n

interface MOTDManager {
    companion object {
        @JvmStatic
        var instance: MOTDManager? = null
            set(value) {
                check(field == null) { "Manager instance has already been set." }

                field = value
            }
    }

    val config: Config

    val i18n: I18n

    fun reloadConfig()
}