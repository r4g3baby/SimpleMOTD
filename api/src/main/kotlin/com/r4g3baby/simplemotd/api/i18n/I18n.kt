package com.r4g3baby.simplemotd.api.i18n

interface I18n {
    fun t(key: String, vararg args: Any, prefixed: Boolean = true) = trans(key, *args, prefixed = prefixed)
    fun trans(key: String, vararg args: Any, prefixed: Boolean = true): String
}