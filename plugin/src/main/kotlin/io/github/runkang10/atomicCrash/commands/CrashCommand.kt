package io.github.runkang10.atomicCrash.commands

import com.mojang.brigadier.tree.LiteralCommandNode
import io.github.runkang10.atomicCrash.AtomicCrashAPI
import io.github.runkang10.atomicCrash.configurations.DefaultTranslations
import io.github.runkang10.atomicCrash.services.Coroutine
import io.github.runkang10.atomicCrash.services.Permissions
import io.github.runkang10.atomicCrash.services.Permissions.canCrash
import io.github.runkang10.atomicCrash.services.PrefixedSender.send
import io.github.runkang10.atomicCrash.services.Tags
import io.github.runkang10.compactmono.commands.*
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver

class CrashCommand(private val translations: LoggedConfiguration<DefaultTranslations>) : BrigadierCommand {
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
                val translations = translations.get()

                if (!sender.canCrash(target)) {
                    sender.send(translations.crash.insufficientPermission)
                    return@execute
                }

                Coroutine.launch {
                    AtomicCrashAPI.get()?.crash(target)
                    sender.send(translations.crash.crashed, Tags.default(target.name))
                }
            }
        }
    }.build()
}