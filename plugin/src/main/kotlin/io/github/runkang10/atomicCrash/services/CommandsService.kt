package io.github.runkang10.atomicCrash.services

import io.github.runkang10.atomicCrash.commands.AtomicCrashCommand
import io.github.runkang10.atomicCrash.commands.CrashCommand
import io.github.runkang10.atomicCrash.configurations.current.SettingsConfig
import io.github.runkang10.atomicCrash.configurations.current.TranslationsConfig
import io.github.runkang10.atomicCrash.types.Service
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.bukkit.plugin.java.JavaPlugin

class CommandsService(
    private val logger: ColoredLogger,
    private val plugin: JavaPlugin,
    private val crash: CrashService,
    settings: ConfigService<SettingsConfig>,
    translations: ConfigService<TranslationsConfig>,
) : Service {
    private val coroutine by lazy { CoroutineScope(Dispatchers.IO + SupervisorJob()) }
    private val commands by lazy {
        arrayOf(
            AtomicCrashCommand(coroutine, settings, translations),
            CrashCommand(coroutine, crash, translations)
        )
    }

    override fun load() {
        if (crash.get() == null) return

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

    override fun unload() {
        coroutine.cancel()
    }
}