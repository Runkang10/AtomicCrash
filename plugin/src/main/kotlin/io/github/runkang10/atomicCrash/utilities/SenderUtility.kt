package io.github.runkang10.atomicCrash.utilities

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

object SenderUtility {
    val defaultTags = tags()

    fun tags(target: String = "") = TagResolver.resolver(
        Placeholder.parsed("target", target)
    )
}