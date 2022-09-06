package com.r4g3baby.simplemotd.api.config

import com.r4g3baby.simplemotd.api.config.model.Storage

interface Config {
    val version: Int
    val language: String
    val checkForUpdates: Boolean
    val storage: Storage
}