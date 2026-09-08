package io.github.runkang10.atomicCrash.configurations

import io.github.runkang10.atomicCrash.utilities.transformation
import org.spongepowered.configurate.NodePath
import org.spongepowered.configurate.transformation.ConfigurationTransformation

object SettingsMigrations {
    fun new() = ConfigurationTransformation.versionedBuilder()
        .versionKey("version")
        .addVersion(1, ConfigurationTransformation.empty())
        .addVersion(
            2,
            transformation {
                addAction(NodePath.path()) { _, node ->
                    node.node("check").set(CheckSettings())
                    null
                }
            }
        )
        .build()
}