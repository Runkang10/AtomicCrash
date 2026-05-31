package io.github.runkang10.atomicCrash.shared

interface ModuleHeader {
    val supportedVersion: Array<String>
    fun new(): Module
}