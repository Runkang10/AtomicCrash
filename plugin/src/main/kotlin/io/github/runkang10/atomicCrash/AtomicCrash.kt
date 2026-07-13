package io.github.runkang10.atomicCrash

import io.github.runkang10.atomicCrash.services.CommandsService
import io.github.runkang10.atomicCrash.services.CrashService
import io.github.runkang10.atomicCrash.types.BoostrapServiceHolder
import org.bukkit.plugin.java.JavaPlugin

class AtomicCrash(bootstrapServiceHolder: BoostrapServiceHolder) : JavaPlugin() {
    private val logger = bootstrapServiceHolder.logger
    private val settings = bootstrapServiceHolder.settings
    private val translations = bootstrapServiceHolder.translations
    private val crash = CrashService(this, logger)
    private val commands = CommandsService(logger, this, crash, settings, translations)


    override fun onLoad() {
        crash.load()
        if (crash.get() == null) {
            server.pluginManager.disablePlugin(this)
            return
        }

        commands.load()
    }

    override fun onDisable() {
        commands.unload()
        crash.unload()
    }
}
