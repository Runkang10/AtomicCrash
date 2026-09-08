package io.github.runkang10.atomicCrash

import io.github.runkang10.atomicCrash.configurations.DefaultSettings
import io.github.runkang10.atomicCrash.configurations.DefaultTranslations
import io.github.runkang10.atomicCrash.listeners.PlayerListener
import io.github.runkang10.atomicCrash.services.AtomicCrashLoader
import io.github.runkang10.atomicCrash.services.Commands
import io.github.runkang10.atomicCrash.services.CrashRegistry
import io.github.runkang10.atomicCrash.services.Permissions
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import io.github.runkang10.compactmono.services.ColoredLogger
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin

class AtomicCrash(
    private val logger: ColoredLogger,
    private val settings: LoggedConfiguration<DefaultSettings>,
    private val translations: LoggedConfiguration<DefaultTranslations>,
) : JavaPlugin() {
    private val commands = Commands(this, settings, translations, pluginMeta)


    override fun onLoad() {
        AtomicCrashLoader.load(logger)

        Permissions.register()
        commands.load()
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(PlayerListener(settings, translations), this)
    }

    override fun onDisable() {
        HandlerList.unregisterAll(this)
        CrashRegistry.clear()

        AtomicCrashLoader.unload()
    }
}