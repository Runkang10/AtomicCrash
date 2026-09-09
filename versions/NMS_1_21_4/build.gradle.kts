import io.papermc.paperweight.userdev.ReobfArtifactConfiguration

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.paperweightUserdev)
}

dependencies {
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
    compileOnly(project(":shared"))
}

paperweight {
    javaLauncher = javaToolchains.launcherFor {
        languageVersion = JavaLanguageVersion.of(21)
    }

    reobfArtifactConfiguration = ReobfArtifactConfiguration.MOJANG_PRODUCTION
}