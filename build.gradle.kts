plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.paperweightUserdev) apply false
}

allprojects {
    repositories {
        gradlePluginPortal()
        mavenCentral()

        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.opencollab.dev/main/")
    }
}

kotlin {
    jvmToolchain(25)
}