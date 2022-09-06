package com.r4g3baby.simplemotd.api.storage.model

interface Driver {
    val name: String
    val fileName: String
    val mavenPath: String

    fun validateHash(otherHash: ByteArray): Boolean
}