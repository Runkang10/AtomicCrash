package io.github.runkang10.atomicCrash.commands

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import io.github.runkang10.atomicCrash.configurations.current.SettingsConfig
import io.github.runkang10.atomicCrash.configurations.current.TranslationsConfig
import io.github.runkang10.atomicCrash.services.ConfigService
import io.github.runkang10.atomicCrash.types.commands.Command
import io.github.runkang10.atomicCrash.types.commands.CommandMeta
import io.github.runkang10.atomicCrash.types.commands.MultiSender
import io.github.runkang10.atomicCrash.utilities.PermissionUtility
import io.github.runkang10.compactmono.commands.command
import io.github.runkang10.compactmono.commands.execute
import io.github.runkang10.compactmono.commands.permission
import io.github.runkang10.compactmono.commands.subcommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.permissions.PermissionDefault

class AtomicCrashCommand(
    private val coroutine: CoroutineScope,
    private val settings: ConfigService<SettingsConfig>,
    private val translations: ConfigService<TranslationsConfig>
) : Command {
    companion object {
        private val PERMISSION = PermissionUtility.from("command", "atomiccrash")
        private val RELOAD_PERMISSION = PermissionUtility.from("command", "atomiccrash", "reload")
    }


    override fun meta() = CommandMeta("AtomicCrash command.")

    override fun execute(): LiteralCommandNode<CommandSourceStack> = command("atomiccrash") {
        permission(PERMISSION, PermissionDefault.OP)

        subcommand("reload") {
            permission(RELOAD_PERMISSION, PermissionDefault.OP)
            execute(::reload)
        }
    }.build()


    private fun reload(context: CommandContext<CommandSourceStack>) {
        val t = translations.get()

        val source = context.source
        val sender = MultiSender(t.prefix, source.executor, source.sender)

        sender.send(t.reload.before)
        coroutine.launch(Dispatchers.IO) {
            val settingsResult = settings.reload()
            val translationsResult = translations.reload()

            if (settingsResult.isFailure || translationsResult.isFailure)
                sender.send(t.reload.failure)
            else sender.send(t.reload.success)
        }
    }
}