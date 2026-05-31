package io.github.runkang10.atomicCrash.types.commands

import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack

interface Command {
    fun meta(): CommandMeta
    fun execute(): LiteralCommandNode<CommandSourceStack>
}