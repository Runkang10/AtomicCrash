package io.github.runkang10.atomicCrash.services

import io.github.runkang10.atomicCrash.commands.AtomicCrashCommand
import io.github.runkang10.atomicCrash.commands.CrashCommand
import io.github.runkang10.atomicCrash.configurations.DefaultSettings
import io.github.runkang10.atomicCrash.configurations.DefaultTranslations
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import io.github.runkang10.compactmono.services.GenericService
import io.papermc.paper.plugin.configuration.PluginMeta
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.plugin.java.JavaPlugin

class Commands(
    private val plugin: JavaPlugin,
    private val settings: LoggedConfiguration<DefaultSettings>,
    private val translations: LoggedConfiguration<DefaultTranslations>,
    private val pluginMeta: PluginMeta
) : GenericService {
    private val commands by lazy {
        arrayOf(
            AtomicCrashCommand(settings, translations, pluginMeta),
            CrashCommand(translations)
        )
    }


    override fun load() {
        val lifecycleManager = plugin.lifecycleManager
        val pluginMeta = plugin.pluginMeta
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            commands.forEach { command ->
                val (description, aliases) = command.meta()
                event.registrar().register(pluginMeta, command.execute(), description, aliases)
            }
        }
    }

    override fun unload() {}
}