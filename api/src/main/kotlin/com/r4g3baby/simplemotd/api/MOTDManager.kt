package com.r4g3baby.simplemotd.api

import com.r4g3baby.simplemotd.api.config.Config
import com.r4g3baby.simplemotd.api.i18n.I18n

interface MOTDManager {
    companion object {
        @JvmStatic
        var instance: MOTDManager? = null
            set(value) {
                check(field == null) { "Instance has already been set." }

                field = value
            }
    }

    fun getConfig(): Config

    fun getI18n(): I18n

    fun reloadConfig()
}