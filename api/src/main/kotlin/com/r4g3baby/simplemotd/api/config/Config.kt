package com.r4g3baby.simplemotd.api.config

interface Config {
    val version: Int
    val language: String
    val checkForUpdates: Boolean
}