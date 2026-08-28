plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.paperweightUserdev) apply false
}

allprojects {
    repositories {
        gradlePluginPortal()
        mavenCentral()

        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

kotlin {
    jvmToolchain(25)
}