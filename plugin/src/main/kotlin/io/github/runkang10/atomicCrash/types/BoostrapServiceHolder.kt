package io.github.runkang10.atomicCrash.types

import io.github.runkang10.atomicCrash.configurations.current.SettingsConfig
import io.github.runkang10.atomicCrash.configurations.current.TranslationsConfig
import io.github.runkang10.atomicCrash.services.ConfigService
import io.github.runkang10.compactmono.services.ColoredLogger

data class BoostrapServiceHolder(
    val logger: ColoredLogger,
    val settings: ConfigService<SettingsConfig>,
    val translations: ConfigService<TranslationsConfig>
)
