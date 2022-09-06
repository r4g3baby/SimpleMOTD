package com.r4g3baby.simplemotd.api.storage

import com.r4g3baby.simplemotd.api.storage.model.Driver
import java.net.InetAddress

interface Storage {
    val driver: Driver?

    fun getPlayerUsername(ip: InetAddress): String?
    fun setPlayerUsername(ip: InetAddress, username: String)
}