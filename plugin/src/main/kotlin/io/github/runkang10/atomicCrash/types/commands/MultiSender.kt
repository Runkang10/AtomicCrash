package io.github.runkang10.atomicCrash.types.commands

import io.github.runkang10.atomicCrash.utilities.PermissionUtility.canCrash
import io.github.runkang10.atomicCrash.utilities.SenderUtility
import io.github.runkang10.atomicCrash.utilities.miniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

class MultiSender(
    private val prefix: String,
    private val executor: Entity?,
    private val sender: CommandSender
) {
    fun send(
        message: String,
        tags: TagResolver = SenderUtility.tags()
    ) {
        val component = miniMessage.deserialize(prefix + message, tags)
        if (executor == sender)
            sender.sendMessage(component)
        else {
            executor?.sendMessage(component)
            sender.sendMessage(component)
        }
    }

    fun isSame(target: Player) = (executor ?: sender) == target

    fun canCrash(target: Player) = (executor ?: sender).canCrash(target)
}