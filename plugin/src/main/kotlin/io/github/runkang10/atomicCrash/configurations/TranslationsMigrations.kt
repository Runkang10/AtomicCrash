package io.github.runkang10.atomicCrash.configurations

import io.github.runkang10.atomicCrash.utilities.transformation
import org.spongepowered.configurate.NodePath
import org.spongepowered.configurate.transformation.ConfigurationTransformation

object TranslationsMigrations {
    fun new() = ConfigurationTransformation.versionedBuilder()
        .versionKey("version")
        .addVersion(1, ConfigurationTransformation.empty())
        .addVersion(
            2,
            transformation {
                addAction(NodePath.path("crash")) { _, node ->
                    val translations = CrashTranslations()
                    node.node("sent-packets").set(translations.sentPackets)
                    node.node("checking").set(translations.checking)
                    node.node("not-crashed").set(translations.notCrashed)
                    null
                }
            }
        )
        .build()
}