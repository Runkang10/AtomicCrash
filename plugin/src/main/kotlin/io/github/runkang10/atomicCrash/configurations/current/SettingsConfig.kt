package io.github.runkang10.atomicCrash.configurations.current

import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class SettingsConfig(
    val version: Int = VERSION
) {
    companion object {
        const val VERSION = 1
    }
}
