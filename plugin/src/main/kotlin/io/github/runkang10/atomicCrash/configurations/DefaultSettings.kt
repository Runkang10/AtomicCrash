package io.github.runkang10.atomicCrash.configurations

import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class DefaultSettings(
    val version: Int = VERSION,
) {
    companion object {
        const val VERSION = 1
    }
}
