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
    val bedrockPlayer: String = "<red>You cannot crash Bedrock players!",
    val insufficientPermission: String = "<red>You don't have permission to crash this player!",
    val sentPackets: String = "<aqua><target><green> has received the bad packets!",
    val checking: String = "<yellow>Checking <aqua><target></aqua> status... The result <b>may not</b> be 100% accurate.",
    val crashed: String = "<aqua><target><green> has been crashed!",
    val notCrashed: String = "<aqua><target><red> is not crashed!",
)

@ConfigSerializable
data class ReloadTranslations(
    val reloading: String = "<yellow>Reloading configurations...",
    val reloaded: String = "<green>All configurations have been reloaded!",
    val reloadFailure: String = "<red>Failed to reload configurations! Check console for details."
)