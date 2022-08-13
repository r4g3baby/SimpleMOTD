package com.r4g3baby.simplemotd.api

import java.io.File
import java.util.logging.Logger

interface MOTDPlugin {
    fun getManager(): MOTDManager

    fun getLogger(): Logger

    fun getDataFolder(): File
}