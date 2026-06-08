package io.github.runkang10.atomicCrash.configurations.migrations

import io.github.runkang10.atomicCrash.configurations.current.SettingsConfig
import org.spongepowered.configurate.transformation.ConfigurationTransformation

object SettingsMigration {
    fun build() = ConfigurationTransformation.versionedBuilder()
        .versionKey("version")
        .addVersion(1, v1Tov2())
        .addVersion(SettingsConfig.VERSION, default())
        .build()


    private fun default() = ConfigurationTransformation.empty()

    private fun v1Tov2() = ConfigurationTransformation.empty()
}