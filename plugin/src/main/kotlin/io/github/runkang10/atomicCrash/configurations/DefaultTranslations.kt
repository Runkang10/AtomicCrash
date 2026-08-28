package io.github.runkang10.atomicCrash.configurations

import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class DefaultTranslations(
    val version: Int = DefaultSettings.VERSION,
    val prefix: String = "<aqua><b>[AtomicCrash]</b></aqua> <dark_gray>» </dark_gray>",
    val crash: CrashTranslations = CrashTranslations(),
    val reload: ReloadTranslations = ReloadTranslations()
)

@ConfigSerializable
data class CrashTranslations(
    val insufficientPermission: String = "<red>You don't have permission to crash this player!",
    val crashed: String = "<aqua><target><green> has been crashed!"
)

@ConfigSerializable
data class ReloadTranslations(
    val reloading: String = "<yellow>Reloading configurations...",
    val reloaded: String = "<green>All configurations have been reloaded!",
    val reloadFailure: String = "<red>Failed to reload configurations! Check console for details."
)