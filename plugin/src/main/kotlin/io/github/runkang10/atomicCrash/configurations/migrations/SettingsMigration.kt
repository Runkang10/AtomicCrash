package io.github.runkang10.atomicCrash.configurations.migrations

import org.spongepowered.configurate.transformation.ConfigurationTransformation

object SettingsMigration {
    fun build() = ConfigurationTransformation.versionedBuilder()
        .versionKey("version")
        .addVersion(1, default())
        .build()


    private fun default() = ConfigurationTransformation.empty()
}