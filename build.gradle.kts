plugins {
    kotlin("jvm") version "2.3.21"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.21" apply false
}

allprojects{
    repositories {
        gradlePluginPortal()
        mavenCentral()

        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

kotlin {
    jvmToolchain(21)
}
