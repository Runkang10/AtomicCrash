package io.github.runkang10.atomicCrash.dsl

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.github.runkang10.atomicCrash.utilities.PermissionUtility
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import org.bukkit.permissions.PermissionDefault

typealias Literal = LiteralArgumentBuilder<CommandSourceStack>
typealias Argument <T> = RequiredArgumentBuilder<CommandSourceStack, T>

inline fun command(
    name: String,
    builder: Literal.() -> Unit
) = Commands.literal(name).apply(builder)


fun Literal.subcommand(
    name: String,
    builder: Literal.() -> Unit
) {
    then(Commands.literal(name).apply(builder))
}

fun <T : Any> Argument<T>.subcommand(
    name: String,
    builder: Literal.() -> Unit
) {
    then(Commands.literal(name).apply(builder))
}


fun <T : Any> Literal.argument(
    name: String,
    argument: ArgumentType<T>,
    builder: Argument<T>.() -> Unit
) {
    then(Commands.argument(name, argument).apply(builder))
}

fun <T : Any, S : Any> Argument<S>.argument(
    name: String,
    argument: ArgumentType<T>,
    builder: Argument<T>.() -> Unit
) {
    then(Commands.argument(name, argument).apply(builder))
}


fun Literal.execute(block: (CommandContext<CommandSourceStack>) -> Unit): Literal = executes {
    block(it)
    1
}

fun <T> Argument<T>.execute(block: (CommandContext<CommandSourceStack>) -> Unit): Argument<T> = executes {
    block(it)
    1
}


fun Literal.permission(
    permission: String,
    default: PermissionDefault,
    condition: () -> Boolean = { true }
): LiteralArgumentBuilder<CommandSourceStack> = requires {
    val sender = it.sender
    val permission = PermissionUtility.register(permission, default)
    sender.hasPermission(permission) && condition()
}

fun <T> Argument<T>.permission(
    permission: String,
    default: PermissionDefault,
    condition: () -> Boolean = { true }
): Argument<T> = requires {
    val sender = it.sender
    val permission = PermissionUtility.register(permission, default)
    sender.hasPermission(permission) && condition()
}