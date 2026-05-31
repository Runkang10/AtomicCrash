package io.github.runkang10.atomicCrash.services

import io.github.runkang10.atomicCrash.modules.NMS_26_1
import io.github.runkang10.atomicCrash.shared.Module
import org.bukkit.plugin.java.JavaPlugin

class CrashService(
    private val plugin: JavaPlugin,
    private val logger: ColoredLogger
) {
    private val availableModules by lazy { arrayOf(NMS_26_1) }
    private var module: Module? = null


    fun load() {
        logger.loading("Crash")

        val serverVersion = plugin.server.minecraftVersion
        for (moduleHeader in availableModules) {
            val supportedVersion = moduleHeader.supportedVersion
            if (!supportedVersion.any { it == serverVersion }) continue

            module = moduleHeader.new()
            break
        }

        if (module == null) {
            logger.error("Unable to find a compatible Crash module! Does the plugin support the server version?")
            return
        }

        logger.loaded("Crash")
    }

    fun get(): Module? = module

    fun unload() {
        logger.unloading("Crash")
        module = null
        logger.unloaded("Crash")
    }
}