package io.github.runkang10.atomicCrash.commands

import com.mojang.brigadier.tree.LiteralCommandNode
import io.github.runkang10.atomicCrash.AtomicCrashAPI
import io.github.runkang10.atomicCrash.configurations.DefaultSettings
import io.github.runkang10.atomicCrash.configurations.DefaultTranslations
import io.github.runkang10.atomicCrash.services.Coroutine
import io.github.runkang10.atomicCrash.services.CrashRegistry
import io.github.runkang10.atomicCrash.services.Permissions
import io.github.runkang10.atomicCrash.services.Permissions.canCrash
import io.github.runkang10.atomicCrash.services.PrefixedSender.send
import io.github.runkang10.atomicCrash.utilities.Tags
import io.github.runkang10.atomicCrash.utilities.isFloodgatePlayer
import io.github.runkang10.compactmono.commands.*
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class CrashCommand(
    private val settings: LoggedConfiguration<DefaultSettings>,
    private val translations: LoggedConfiguration<DefaultTranslations>
) : BrigadierCommand {
    override fun meta() = BrigadierCommandMeta("Crash players.")

    override fun execute(): LiteralCommandNode<CommandSourceStack> = command("crash") {
        permission(Permissions.CRASH.permission) { AtomicCrashAPI.get() != null }
        argument("target", ArgumentTypes.player()) {
            execute { context ->
                val source = context.source

                val sender = source.executor ?: source.sender
                val target = context.getArgument("target", PlayerSelectorArgumentResolver::class.java)
                    .resolve(source)
                    .first()

                val settings = settings.get().check
                val translations = translations.get().crash
                val tags = Tags.default(target.name)

                if (target.isFloodgatePlayer()) {
                    sender.send(translations.bedrockPlayer, tags)
                    return@execute
                }

                if (!sender.canCrash(target)) {
                    sender.send(translations.insufficientPermission, tags)
                    return@execute
                }

                Coroutine.launch {
                    AtomicCrashAPI.get()?.crash(target)
                    sender.send(translations.sentPackets, tags)

                    if (!settings.enabled) return@launch
                    sender.send(translations.checking, tags)

                    delay(750.milliseconds)
                    CrashRegistry.add(sender, target)

                    delay(settings.timeout.coerceIn(1, 60).seconds)
                    if (CrashRegistry.remove(target.uniqueId) == null) return@launch

                    sender.send(translations.crashed, tags)
                }
            }
        }
    }.build()
}