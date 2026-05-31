package io.github.runkang10.atomicCrash.types.commands

data class CommandMeta(
    val description: String,
    val aliases: List<String> = emptyList()
)
