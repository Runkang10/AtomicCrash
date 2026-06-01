package io.github.runkang10.atomicCrash.commands

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import io.github.runkang10.atomicCrash.configurations.current.TranslationsConfig
import io.github.runkang10.atomicCrash.services.ConfigService
import io.github.runkang10.atomicCrash.services.CrashService
import io.github.runkang10.atomicCrash.types.commands.Command
import io.github.runkang10.atomicCrash.types.commands.CommandMeta
import io.github.runkang10.atomicCrash.types.commands.MultiSender
import io.github.runkang10.atomicCrash.utilities.CommandUtility.execute
import io.github.runkang10.atomicCrash.utilities.CommandUtility.permission
import io.github.runkang10.atomicCrash.utilities.PermissionUtility
import io.github.runkang10.atomicCrash.utilities.Schedulers
import io.github.runkang10.atomicCrash.utilities.SenderUtility
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import org.bukkit.permissions.PermissionDefault

class CrashCommand(
    private val crash: CrashService,
    private val translations: ConfigService<TranslationsConfig>
) : Command {
    companion object {
        private val EXECUTE_PERMISSION = PermissionUtility.from("command", "crash")
    }

    override fun meta() = CommandMeta("Crash players with this command.")

    override fun execute(): LiteralCommandNode<CommandSourceStack> =
        Commands.literal("crash")
            .permission(EXECUTE_PERMISSION, PermissionDefault.OP)
            .requires { crash.get() != null }
            .then(Commands.argument("target", ArgumentTypes.player()).execute(::execute))
            .build()


    private fun execute(context: CommandContext<CommandSourceStack>) {
        val t = translations.get()

        val source = context.source
        val sender = MultiSender(t.prefix, source.executor, source.sender)
        val target = runCatching {
            context.getArgument("target", PlayerSelectorArgumentResolver::class.java)
                .resolve(source)
                .firstOrNull()
        }.getOrNull() ?: return

        val tags = SenderUtility.tags(target.name)
        if (sender.isSame(target))
            sender.send(t.crash.selfCrash, tags)
        if (!sender.canCrash(target))
            sender.send(t.crash.exempt, tags)
        else Schedulers.async.runNow {
            sender.send(t.crash.before, tags)

            val crashModule = crash.get()
            if (crashModule == null) {
                sender.send(t.crash.failure, tags)
                return@runNow
            }

            crashModule.crash(target).onSuccess {
                sender.send(t.crash.success, tags)
            }.onFailure {
                sender.send(t.crash.failure, tags)
            }
        }
    }
}