package io.github.runkang10.atomicCrash.services

import io.github.runkang10.atomicCrash.commands.AtomicCrashCommand
import io.github.runkang10.atomicCrash.commands.CrashCommand
import io.github.runkang10.atomicCrash.configurations.current.SettingsConfig
import io.github.runkang10.atomicCrash.configurations.current.TranslationsConfig
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.plugin.java.JavaPlugin

class CommandsService(
    private val logger: ColoredLogger,
    private val plugin: JavaPlugin,
    crash: CrashService,
    settings: ConfigService<SettingsConfig>,
    translations: ConfigService<TranslationsConfig>,
) {
    private val commands = arrayOf(
        AtomicCrashCommand(settings, translations),
        CrashCommand(crash, translations)
    )

    fun load() {
        logger.loading("Commands")

        plugin.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) {
            val registrar = it.registrar()
            commands.forEach { command ->
                val (description, aliases) = command.meta()
                registrar.register(
                    plugin.pluginMeta,
                    command.execute(),
                    description,
                    aliases
                )
            }
        }

        logger.loaded("Commands")
    }
}