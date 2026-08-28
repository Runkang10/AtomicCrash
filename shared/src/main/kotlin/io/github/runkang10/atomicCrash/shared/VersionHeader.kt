package io.github.runkang10.atomicCrash.shared

interface VersionHeader {
    val supportedVersion: List<String>

    fun new(): Version
}