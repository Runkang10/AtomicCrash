package io.github.runkang10.atomicCrash.services

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

object Tags {
    val EMPTY = TagResolver.empty()

    fun default(target: String = "N/A") = TagResolver.resolver(Placeholder.unparsed("target", target))
}