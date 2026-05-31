package io.github.runkang10.atomicCrash.services

import io.github.runkang10.atomicCrash.utilities.miniMessage
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.logger.slf4j.ComponentLogger
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

class ColoredLogger(
    private val logger: ComponentLogger
) {
    fun info(
        content: String,
        tags: TagResolver = TagResolver.resolver()
    ) =
        log("<primary>", logger::info, content, tags)

    fun success(
        content: String,
        tags: TagResolver = TagResolver.resolver()
    ) =
        log("<success>", logger::info, content, tags)

    fun warning(
        content: String,
        tags: TagResolver = TagResolver.resolver()
    ) =
        log("<warning>", logger::warn, content, tags)

    fun error(
        content: String,
        tags: TagResolver = TagResolver.resolver()
    ) =
        log("<danger>", logger::error, content, tags)


    fun loading(name: String) = info("Loading $name service...")
    fun loaded(name: String) = success("$name service has been loaded.")
    fun unloading(name: String) = info("Unloading $name service...")
    fun unloaded(name: String) = warning("$name service has been unloaded.")


    private fun log(
        color: String,
        logger: (Component) -> Unit,
        content: String,
        tags: TagResolver
    ) {
        logger(miniMessage.deserialize("$color$content", tags))
    }
}