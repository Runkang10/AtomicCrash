package io.github.runkang10.atomicCrash.configurations.current

import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Setting

@ConfigSerializable
data class TranslationsConfig(
    val prefix: String = "<secondary>[<primary>Atomic<success>Crash<secondary>] <reset>",
    val crash: CrashTranslations = CrashTranslations(),
    val reload: ResultTranslations = ResultTranslations(
        "<warning>Reloading configurations...",
        "<success>Successfully reloaded configurations.",
        "<danger>Failed to reload configuration!"
    ),
    val version: Int = VERSION
) {
    companion object {
        const val VERSION = 1
    }

    @ConfigSerializable
    data class CrashTranslations(
        @Setting("self-crash")
        val selfCrash: String = "<danger>You cannot crash yourself!",
        val exempt: String = "<danger>You cannot crash <primary><target></primary>!",
        val before: String = "<danger>Sending packets to <primary><target></primary>...",
        val success: String = "<success>Successfully sent packets to <primary><target></primary>.",
        val failure: String = "<danger>Failed to send packets to <primary><target></primary>!"
    )

    @ConfigSerializable
    data class ResultTranslations(
        val before: String,
        val success: String,
        val failure: String
    )
}
