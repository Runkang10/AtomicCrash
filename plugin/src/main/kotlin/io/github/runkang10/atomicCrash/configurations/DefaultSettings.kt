package io.github.runkang10.atomicCrash.configurations

import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment

@ConfigSerializable
data class DefaultSettings(
    val version: Int = VERSION,
    val check: CheckSettings = CheckSettings()
) {
    companion object {
        const val VERSION = 2
    }
}

@ConfigSerializable
data class CheckSettings(
    @Comment("Should the plugin check if the player is crashed or not?")
    val enabled: Boolean = true,
    @Comment(
        "Maximum time in seconds to wait for the player to respond to determine if the player is crashed or not.\n" +
                "You can only choose between 1 and 60 seconds."
    )
    val timeout: Int = 5
)