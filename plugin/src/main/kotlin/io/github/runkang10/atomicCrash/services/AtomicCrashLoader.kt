package io.github.runkang10.atomicCrash

import io.github.runkang10.atomicCrash.version.NMS_26
import io.github.runkang10.compactmono.services.ColoredLogger
import org.bukkit.Bukkit

object AtomicCrashLoader {
    fun load(logger: ColoredLogger) {
        val versions = listOf(NMS_26, NMS_26)

        val serverVersion = Bukkit.getMinecraftVersion()
        val version = versions.find { it.supportedVersion.contains(serverVersion) }
        if (version == null) {
            logger.error("Could not find supported version for $serverVersion!")
            logger.error("Supported versions: ${versions.map { it.supportedVersion }}")
            return
        }

        AtomicCrashAPI.set(version.new())
    }
}