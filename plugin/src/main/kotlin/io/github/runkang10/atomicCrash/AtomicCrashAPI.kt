package io.github.runkang10.atomicCrash

import io.github.runkang10.atomicCrash.shared.Version

object AtomicCrashAPI {
    @Volatile
    private var nms: Version? = null


    @JvmStatic
    internal fun set(version: Version?) {
        nms = version
    }

    @JvmStatic
    fun get() = nms
}