package io.github.runkang10.atomicCrash

import io.github.runkang10.atomicCrash.services.CrashService

object AtomicCrashAPI {
    private var crashService: CrashService? = null


    @JvmStatic
    fun set(service: CrashService?) {
        crashService = service
    }

    @JvmStatic
    fun get() = crashService?.get()
}