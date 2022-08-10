package com.r4g3baby.simplemotd.api.config

interface Config {
    fun getVersion(): Int
    fun getLanguage(): String
    fun checkForUpdates(): Boolean
}