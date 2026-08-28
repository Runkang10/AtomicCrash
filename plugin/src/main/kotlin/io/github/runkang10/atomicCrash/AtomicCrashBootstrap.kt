package io.github.runkang10.atomicCrash

import io.github.runkang10.atomicCrash.configurations.DefaultSettings
import io.github.runkang10.atomicCrash.configurations.DefaultTranslations
import io.github.runkang10.atomicCrash.services.PrefixedSender
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import io.github.runkang10.compactmono.services.ColoredLogger
import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.bootstrap.PluginProviderContext
import org.spongepowered.configurate.ConfigurationOptions
import java.io.File

@Suppress("unused", "UnstableApiUsage")
internal class AtomicCrashBootstrap : PluginBootstrap {
    private lateinit var logger: ColoredLogger
    private lateinit var settings: LoggedConfiguration<DefaultSettings>
    private lateinit var translations: LoggedConfiguration<DefaultTranslations>


    override fun bootstrap(context: BootstrapContext) {
        logger = ColoredLogger(context.logger)

        val pluginFolder = context.dataDirectory.toFile()
        settings = LoggedConfiguration(
            File(pluginFolder, "settings.conf"),
            DefaultSettings::class,
            DefaultSettings(),
            ConfigurationOptions.defaults(),
            null,
            logger
        )
        translations = LoggedConfiguration(
            File(pluginFolder, "translations.conf"),
            DefaultTranslations::class,
            DefaultTranslations(),
            ConfigurationOptions.defaults(),
            null,
            logger
        )
        settings.load()
        translations.load()
        PrefixedSender.PREFIX = translations.get().prefix
    }

    override fun createPlugin(context: PluginProviderContext) = AtomicCrash(logger, settings, translations)
}