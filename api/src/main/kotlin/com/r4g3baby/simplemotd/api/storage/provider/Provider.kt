package com.r4g3baby.simplemotd.api.storage.provider

import java.net.InetAddress

interface Provider {
    fun init()
    fun shutdown()

    fun getPlayerUsername(ip: InetAddress): String?
    fun setPlayerUsername(ip: InetAddress, username: String)
}