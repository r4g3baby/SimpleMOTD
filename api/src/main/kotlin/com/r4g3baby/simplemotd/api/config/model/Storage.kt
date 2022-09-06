package com.r4g3baby.simplemotd.api.config.model

interface Storage {
    val driver: String
    val tablePrefix: String

    val address: String
    val database: String
    val username: String
    val password: String

    val pool: Pool

    interface Pool {
        val maximumPoolSize: Int
        val minimumIdle: Int
        val maxLifetime: Long
        val keepaliveTime: Long
        val connectionTimeout: Long
        val extraProperties: Map<String, Any>
    }
}