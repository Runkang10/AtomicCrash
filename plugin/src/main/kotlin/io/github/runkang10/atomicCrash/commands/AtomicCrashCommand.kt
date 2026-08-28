package io.github.runkang10.atomicCrash.commands

import com.mojang.brigadier.tree.LiteralCommandNode
import io.github.runkang10.atomicCrash.AtomicCrashAPI
import io.github.runkang10.atomicCrash.configurations.DefaultSettings
import io.github.runkang10.atomicCrash.configurations.DefaultTranslations
import io.github.runkang10.atomicCrash.services.Coroutine
import io.github.runkang10.atomicCrash.services.Permissions
import io.github.runkang10.atomicCrash.services.PrefixedSender
import io.github.runkang10.atomicCrash.services.PrefixedSender.send
import io.github.runkang10.compactmono.commands.*
import io.github.runkang10.compactmono.configuration.IConfiguration
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.plugin.configuration.PluginMeta

class AtomicCrashCommand(
    private val settings: LoggedConfiguration<DefaultSettings>,
    private val translations: LoggedConfiguration<DefaultTranslations>,
    pluginMeta: PluginMeta
) : BrigadierCommand {
    private val apiStatus = if (AtomicCrashAPI.get() != null) "<green>enabled</green>" else "<red>disabled</red>"
    private val infoMessage = listOf(
        "<aqua><b>===== AtomicCrash =====</b></aqua>",
        "<yellow>Author: <aqua><u>${pluginMeta.authors.first()}</u></aqua>",
        "<yellow>Version: <aqua><i>${pluginMeta.version}</i></aqua>",
        "<yellow>Status: <i>$apiStatus</i>"
    )


    override fun meta() = BrigadierCommandMeta("AtomicCrash command.", listOf("ac"))

    override fun execute(): LiteralCommandNode<CommandSourceStack> = command("atomiccrash") {
        permission(Permissions.Core.COMMAND.permission)
        subcommand("info") {
            permission(Permissions.Core.INFO.permission)
            execute { context ->
                val sender = context.source.executor ?: context.source.sender
                infoMessage.forEach { line -> sender.sendRichMessage(line) }
            }
        }
        subcommand("reload") {
            permission(Permissions.Core.RELOAD.permission)
            execute { context ->
                val sender = context.source.executor ?: context.source.sender
                val reloadTranslations = translations.get().reload

                sender.send(reloadTranslations.reloading)
                Coroutine.launch {
                    val settingsResult = settings.load()
                    val translationsResult = translations.load()

                    if (settingsResult is IConfiguration.Result.Failure || translationsResult is IConfiguration.Result.Failure) {
                        sender.send(reloadTranslations.reloadFailure)
                        return@launch
                    }

                    sender.send(reloadTranslations.reloaded)
                    @Suppress("UNCHECKED_CAST")
                    PrefixedSender.PREFIX = translations.get().prefix
                }
            }
        }
    }.build()
}