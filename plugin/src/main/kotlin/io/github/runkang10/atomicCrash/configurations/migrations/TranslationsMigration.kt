package io.github.runkang10.atomicCrash.configurations.migrations

import io.github.runkang10.atomicCrash.configurations.current.TranslationsConfig
import org.spongepowered.configurate.NodePath
import org.spongepowered.configurate.transformation.ConfigurationTransformation

object TranslationsMigration {
    fun build() = ConfigurationTransformation.versionedBuilder()
        .versionKey("version")
        .addVersion(2, v1Tov2())
        .build()


    private fun v1Tov2() = ConfigurationTransformation.builder()
        .addAction(NodePath.path()) { _, node ->
            val crashTranslations = TranslationsConfig.CrashTranslations()

            node.node("crash", "not-enabled").set(crashTranslations.notEnabled)

            val crashBeforeNode = node.node("crash", "before")
            node.node("crash", "sending").set(crashBeforeNode?.string ?: crashTranslations.sending)
            node.node("crash").removeChild("before")
            null
        }
        .build()
}