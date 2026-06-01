plugins {
    kotlin("jvm")
    id("com.gradleup.shadow") version "9.4.2"
    id("xyz.jpenilla.run-paper") version "3.0.2"
}

val projectPackage = "io.github.runkang10.atomicCrash"
description = "A different way to surprise cheaters."
version = "1.0.0"

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.1.2.build.+")
    implementation(kotlin("stdlib"))
    implementation(project(":shared"))
    rootDir.resolve("modules").listFiles()
        .filter { it.isDirectory }
        .forEach { implementation(project(":modules:${it.name}")) }
    implementation("org.spongepowered:configurate-yaml:4.2.0")
    implementation("org.spongepowered:configurate-extra-kotlin:4.2.0")
}

kotlin {
    jvmToolchain(25)
}

tasks {
    runServer {
        minecraftVersion("26.1.2")
        jvmArgs("-Xms2G", "-Xmx2G")
    }

    shadowJar {
        mapOf("org.spongepowered.configurate" to "$projectPackage.dependencies.configurate").forEach { (original, new) ->
            relocate(original, new)
        }

        archiveBaseName.set(rootProject.name)
        archiveVersion.set(version.toString())
        archiveClassifier.set("")
    }

    processResources {
        val props = mapOf("version" to version, "description" to description)
        filesMatching("paper-plugin.yml") {
            expand(props)
        }
    }
}
