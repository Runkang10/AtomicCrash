package io.github.runkang10.atomicCrash.utilities

import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

val miniMessage by lazy {
    MiniMessage.builder()
        .editTags { t ->
            t
                .resolver(colorOf("primary", TextColor.fromHexString("#2596be")))
                .resolver(colorOf("secondary", TextColor.fromHexString("#99AAB5")))
                .resolver(colorOf("success", TextColor.fromHexString("#00ffa6")))
                .resolver(colorOf("danger", TextColor.fromHexString("#fc3a3a")))
                .resolver(colorOf("warning", TextColor.fromHexString("#fff700")))
        }
        .build()
}


private fun colorOf(
    name: String,
    color: TextColor?
) = TagResolver.resolver(name, Tag.styling(color ?: NamedTextColor.WHITE))