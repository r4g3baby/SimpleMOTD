package com.r4g3baby.simplemotd.core

import java.io.File
import java.util.logging.Logger

interface MOTDPlugin {
    fun getLogger(): Logger

    fun getDataFolder(): File
}