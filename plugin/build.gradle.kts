import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.shadow)
    alias(libs.plugins.resourceFactoryPaper)
    alias(libs.plugins.runPaper)
    alias(libs.plugins.minotaur)
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.kotlinx.coroutines)
    implementation(libs.compactMono)
    implementation(libs.configurate.hocon)
    implementation(libs.configurate.extra.kotlin)
    implementation(project(":shared"))
    rootDir.resolve("versions")
        .listFiles { it.isDirectory }
        ?.forEach { implementation(project(":versions:${it.name}")) }

    compileOnly("io.papermc.paper:paper-api:26.2.build.+")
}

val projectVersion = System.getenv("version")?.removePrefix("v") ?: "0.0.0-DEV"

paperPluginYaml {
    name.set(rootProject.name)
    description.set("A different way to surprise cheaters.")
    version.set(projectVersion)
    apiVersion.set("26.2")

    bootstrapper.set("io.github.runkang10.atomicCrash.AtomicCrashBootstrap")
    main.set("io.github.runkang10.atomicCrash.AtomicCrash")
    load.set(BukkitPluginYaml.PluginLoadOrder.STARTUP)
    foliaSupported.set(true)

    authors.add("Runkang10")
}

tasks {
    shadowJar {
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")
        archiveVersion.set("")

        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    runServer {
        minecraftVersion("26.2")
        jvmArgs("-Xms2G", "-Xmx2G", "-Dcom.mojang.eula.agree=true")
    }

    if (System.getenv("modrinth")?.toBoolean() ?: false) modrinth {
        token.set(System.getenv("MODRINTH_TOKEN") ?: error("MODRINTH_TOKEN is not set!"))
        projectId.set("ZfLBx8El")

        uploadFile.set(shadowJar)
        versionType.set("release")
        versionName.set(System.getenv("title") ?: "Untitled")
        versionNumber.set(projectVersion)
        changelog.set(System.getenv("changelog") ?: "_No changelog provided._")
        loaders.addAll("paper", "purpur", "folia")
        gameVersions.addAll("1.21.10", "1.21.11", "26.1", "26.1.1", "26.1.2", "26.2")

        syncBodyFrom.set(rootProject.file("README.md").readText())
    }
}