package io.github.runkang10.atomicCrash.utilities

import org.spongepowered.configurate.transformation.ConfigurationTransformation

fun transformation(builder: ConfigurationTransformation.Builder.() -> Unit): ConfigurationTransformation =
    ConfigurationTransformation.builder().apply(builder).build()