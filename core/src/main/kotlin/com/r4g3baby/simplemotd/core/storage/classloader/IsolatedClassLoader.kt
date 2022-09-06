package com.r4g3baby.simplemotd.core.storage.classloader

import java.net.URL
import java.net.URLClassLoader

class IsolatedClassLoader(urls: Array<URL>) : URLClassLoader(urls, ClassLoader.getSystemClassLoader().parent) {
    companion object {
        init {
            ClassLoader.registerAsParallelCapable()
        }
    }
}