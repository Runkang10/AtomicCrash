package io.github.runkang10.atomicCrash

import io.github.runkang10.atomicCrash.configurations.current.SettingsConfig
import io.github.runkang10.atomicCrash.configurations.current.TranslationsConfig
import io.github.runkang10.atomicCrash.configurations.migrations.SettingsMigration
import io.github.runkang10.atomicCrash.services.ColoredLogger
import io.github.runkang10.atomicCrash.services.ConfigService
import io.github.runkang10.atomicCrash.types.BoostrapServiceHolder
import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.bootstrap.PluginProviderContext


@Suppress("unused", "UnstableApiUsage")
class AtomicCrashBootstrap : PluginBootstrap {
    private lateinit var bootstrapServiceHolder: BoostrapServiceHolder

    override fun bootstrap(context: BootstrapContext) {
        val logger = ColoredLogger(context.logger)

        val dataDirectory = context.dataDirectory.toFile()
        val settings = ConfigService(
            logger,
            dataDirectory.resolve("settings.yml"),
            SettingsConfig::class.java,
            SettingsConfig(),
            SettingsConfig.VERSION,
            SettingsMigration.build()
        )
        val translations = ConfigService(
            logger,
            dataDirectory.resolve("translations.yml"),
            TranslationsConfig::class.java,
            TranslationsConfig(),
            TranslationsConfig.VERSION
        )
        settings.load()
        translations.load()

        bootstrapServiceHolder = BoostrapServiceHolder(
            logger = logger,
            settings = settings,
            translations = translations
        )
    }

    override fun createPlugin(context: PluginProviderContext) = AtomicCrash(bootstrapServiceHolder)
}
