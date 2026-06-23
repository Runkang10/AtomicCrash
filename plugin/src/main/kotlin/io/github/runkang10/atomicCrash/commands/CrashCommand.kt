package io.github.runkang10.atomicCrash.commands

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import io.github.runkang10.atomicCrash.configurations.current.TranslationsConfig
import io.github.runkang10.atomicCrash.services.ConfigService
import io.github.runkang10.atomicCrash.services.CrashService
import io.github.runkang10.atomicCrash.types.commands.Command
import io.github.runkang10.atomicCrash.types.commands.CommandMeta
import io.github.runkang10.atomicCrash.types.commands.MultiSender
import io.github.runkang10.atomicCrash.utilities.PermissionUtility
import io.github.runkang10.atomicCrash.utilities.SenderUtility
import io.github.runkang10.compactmono.commands.argument
import io.github.runkang10.compactmono.commands.command
import io.github.runkang10.compactmono.commands.execute
import io.github.runkang10.compactmono.commands.permission
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.entity.Player
import org.bukkit.permissions.PermissionDefault

class CrashCommand(
    private val coroutine: CoroutineScope,
    private val crash: CrashService,
    private val translations: ConfigService<TranslationsConfig>
) : Command {
    companion object {
        private val EXECUTE_PERMISSION = PermissionUtility.from("command", "crash")
    }

    override fun meta() = CommandMeta("Crash players with this command.")

    override fun execute(): LiteralCommandNode<CommandSourceStack> = command("crash") {
        permission(EXECUTE_PERMISSION, PermissionDefault.OP) { crash.get() != null }

        argument("target", ArgumentTypes.player()) {
            execute(::execute)
        }
    }.build()


    private fun execute(context: CommandContext<CommandSourceStack>) {
        val translations = translations.get()

        val source = context.source
        val sender = MultiSender(translations.prefix, source.executor, source.sender)
        val target = runCatching {
            context.getArgument("target", PlayerSelectorArgumentResolver::class.java)
                .resolve(source)
                .firstOrNull()
        }.getOrNull() ?: return

        val tags = SenderUtility.tags(target = target.name)
        val crashTranslations = translations.crash
        if (sender.isSame(target))
            sender.send(crashTranslations.selfCrash, tags)
        else if (!sender.canCrash(target))
            sender.send(crashTranslations.exempt, tags)
        else coroutine.launch(Dispatchers.IO) { crash(sender, target, tags) }
    }

    private fun crash(
        sender: MultiSender,
        target: Player,
        tags: TagResolver
    ) {
        val translations = translations.get()
        val crashTranslations = translations.crash

        sender.send(crashTranslations.sending, tags)

        val crashModule = crash.get() ?: run {
            sender.send(crashTranslations.notEnabled, tags)
            return
        }
        crashModule.crash(target)
            .onSuccess { sender.send(crashTranslations.success, tags) }
            .onFailure { sender.send(translations.crash.failure, tags) }
    }
}