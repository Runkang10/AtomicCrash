package io.github.runkang10.atomicCrash.services

import org.spongepowered.configurate.kotlin.objectMapperFactory
import org.spongepowered.configurate.transformation.ConfigurationTransformation
import org.spongepowered.configurate.yaml.NodeStyle
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import java.io.File

class ConfigService<T>(
    private val logger: ColoredLogger,
    private val file: File,
    private val clazz: Class<T>,
    private val default: T,
    private val currentVersion: Int,
    private val migrations: ConfigurationTransformation.Versioned? = null
) {
    private val loader = YamlConfigurationLoader.builder()
        .file(file)
        .indent(2)
        .nodeStyle(NodeStyle.BLOCK)
        .defaultOptions { options ->
            options.serializers { builder ->
                builder.registerAnnotatedObjects(objectMapperFactory())
            }
        }
        .build()

    private var config: T = default


    fun load(): Result<T> {
        val fileName = file.name
        logger.info("Loading '$fileName'...")

        if (!file.exists()) save()

        return runCatching {
            val node = loader.load()

            migrations?.let { m ->
                val startVersion = m.version(node)
                val endVersion = currentVersion

                if (startVersion < endVersion) {
                    m.apply(node)
                    node.node("version").set(endVersion)
                    loader.save(node)
                    logger.info("Migrated '$fileName' from v$startVersion to v$endVersion.")
                }
            }

            node.get(clazz) ?: default
        }.onSuccess { loaded ->
            config = loaded
            logger.success("Loaded '$fileName'.")
        }.onFailure {
            logger.error("Could not load '$fileName'! Default configuration will be used.")
            logger.error("Caused by: " + (it.message ?: "N/A"))
            it.stackTrace.forEach { element ->
                logger.error(element.toString())
            }
        }
    }

    fun get(): T = config

    fun save() {
        val fileName = file.name
        runCatching {
            file.parentFile?.mkdirs()
            val node = loader.createNode()
            node.set(clazz, config)
            loader.save(node)
        }.onSuccess {
            logger.success("Saved '$fileName'.")
        }.onFailure {
            config = default
            logger.error("Could not save '$fileName'!")
        }
    }

    fun reload(): Result<T> {
        config = default
        return load()
    }
}