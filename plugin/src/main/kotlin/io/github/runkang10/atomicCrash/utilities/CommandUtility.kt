package io.github.runkang10.atomicCrash.utilities

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.permissions.PermissionDefault

object CommandUtility {
    typealias Literal = LiteralArgumentBuilder<CommandSourceStack>
    typealias Argument <T> = RequiredArgumentBuilder<CommandSourceStack, T>

    fun Literal.permission(
        permission: String,
        default: PermissionDefault
    ): LiteralArgumentBuilder<CommandSourceStack> = requires {
        val sender = it.sender
        val permission = PermissionUtility.register(permission, default)
        sender.hasPermission(permission)
    }

    fun <T> Argument<T>.permission(
        permission: String,
        default: PermissionDefault
    ): Argument<T> = requires {
        val sender = it.sender
        val permission = PermissionUtility.register(permission, default)
        sender.hasPermission(permission)
    }


    fun Literal.subcommands(vararg subcommands: Literal): Literal {
        subcommands.forEach { then(it) }
        return this
    }

    fun <T> Argument<T>.subcommands(vararg subcommands: Argument<T>): Argument<T> {
        subcommands.forEach { then(it) }
        return this
    }


    fun Literal.execute(block: (CommandContext<CommandSourceStack>) -> Unit): Literal =
        executes {
            block(it)
            1
        }

    fun <T> Argument<T>.execute(block: (CommandContext<CommandSourceStack>) -> Unit): Argument<T> =
        executes {
            block(it)
            1
        }
}