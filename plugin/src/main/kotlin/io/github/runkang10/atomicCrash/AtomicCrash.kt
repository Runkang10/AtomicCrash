package io.github.runkang10.atomicCrash

import io.github.runkang10.atomicCrash.configurations.DefaultSettings
import io.github.runkang10.atomicCrash.configurations.DefaultTranslations
import io.github.runkang10.atomicCrash.services.AtomicCrashLoader
import io.github.runkang10.atomicCrash.services.Commands
import io.github.runkang10.atomicCrash.services.Permissions
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import io.github.runkang10.compactmono.services.ColoredLogger
import org.bukkit.plugin.java.JavaPlugin

class AtomicCrash(
    private val logger: ColoredLogger,
    settings: LoggedConfiguration<DefaultSettings>,
    translations: LoggedConfiguration<DefaultTranslations>,
) : JavaPlugin() {
    private val commands = Commands(this, settings, translations, pluginMeta)


    override fun onLoad() {
        AtomicCrashLoader.load(logger)

        Permissions.register()
        commands.load()
    }

    override fun onDisable() {
        AtomicCrashAPI.set(null)
    }
}