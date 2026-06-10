import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("com.gradleup.shadow") version "9.4.2"
    id("xyz.jpenilla.run-paper") version "3.0.2"
    id("com.modrinth.minotaur") version "2.+"
}

version = "1.1.1"
description = "A different way to surprise cheaters."

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.1.2.build.+")
    compileOnly("org.spongepowered:configurate-yaml:4.2.0")

    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")
    implementation("org.spongepowered:configurate-extra-kotlin:4.2.0")

    implementation(project(":shared"))
    rootDir.resolve("modules").listFiles()
        .filter { it.isDirectory }
        .forEach { implementation(project(":modules:${it.name}")) }
}

kotlin {
    jvmToolchain(25)
}

tasks {
    runServer {
        minecraftVersion("26.1.2")
        jvmArgs("-Xms1G", "-Xmx2G")
    }

    shadowJar {
        archiveBaseName.set(rootProject.name)
        archiveVersion.set(version.toString())
        archiveClassifier.set("")
    }

    processResources {
        val props = mapOf(
            "projectDescription" to project.description.toString(),
            "projectVersion" to project.version.toString()
        )
        filesMatching("paper-plugin.yml") {
            expand(props)
        }
    }

    modrinth {
        token.set(System.getenv("MODRINTH_TOKEN"))

        projectId.set("ZfLBx8El")
        versionNumber.set(version.toString())
        versionType.set("release")
        uploadFile.set(shadowJar)
        gameVersions.addAll("26.1.2", "26.1.1", "26.1")
        loaders.addAll("paper", "purpur", "folia")
        changelog.set(System.getenv("CHANGELOG"))
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
    freeCompilerArgs.set(listOf("-Xannotation-default-target=param-property"))
}