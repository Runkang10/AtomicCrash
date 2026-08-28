package io.github.runkang10.atomicCrash.services

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.command.CommandSender

object PrefixedSender {
    @Volatile
    var PREFIX: String = ""

    fun CommandSender.send(
        message: String,
        tags: TagResolver = Tags.EMPTY
    ) = sendRichMessage("$PREFIX$message", tags)
}