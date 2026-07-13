package io.github.runkang10.atomicCrash.services

import io.github.runkang10.atomicCrash.AtomicCrashAPI
import io.github.runkang10.atomicCrash.modules.NMS_26_1
import io.github.runkang10.atomicCrash.shared.Module
import io.github.runkang10.compactmono.services.ColoredLogger
import io.github.runkang10.compactmono.services.GenericService
import org.bukkit.plugin.java.JavaPlugin

class CrashService(
    private val plugin: JavaPlugin,
    private val logger: ColoredLogger
) : GenericService {
    private val availableModules by lazy { arrayOf(NMS_26_1) }
    private var module: Module? = null


    override fun load() {
        logger.loading("Crash")

        val serverVersion = plugin.server.minecraftVersion
        for (moduleHeader in availableModules) {
            val supportedVersion = moduleHeader.supportedVersion
            if (!supportedVersion.any { it == serverVersion }) continue

            module = moduleHeader.new()
            break
        }

        if (module == null) {
            logger.error("Unable to find a compatible Crash module! Does the plugin support $serverVersion?")
            return
        }

        AtomicCrashAPI.set(this)
        logger.loaded("Crash")
    }

    fun get(): Module? = module

    override fun unload() {
        logger.unloading("Crash")
        AtomicCrashAPI.set(null)
        module = null
        logger.unloaded("Crash")
    }
}