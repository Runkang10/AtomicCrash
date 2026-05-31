package io.github.runkang10.atomicCrash

import io.github.runkang10.atomicCrash.services.CommandsService
import io.github.runkang10.atomicCrash.services.CrashService
import io.github.runkang10.atomicCrash.types.BoostrapServiceHolder
import io.github.runkang10.atomicCrash.utilities.Schedulers
import org.bukkit.plugin.java.JavaPlugin

class AtomicCrash(bootstrapServiceHolder: BoostrapServiceHolder) : JavaPlugin() {
    private val logger = bootstrapServiceHolder.logger
    private val settings = bootstrapServiceHolder.settings
    private val translations = bootstrapServiceHolder.translations
    private val crash = CrashService(this, logger)
    private val commands by lazy { CommandsService(logger, this, crash, settings, translations) }


    override fun onLoad() {
        Schedulers.load(this)
        crash.load()
        commands.load()
    }

    override fun onDisable() {
        crash.unload()
    }
}
