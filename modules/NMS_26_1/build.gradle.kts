plugins {
    kotlin("jvm")
    id("io.papermc.paperweight.userdev")
}

dependencies {
    paperweight.paperDevBundle("26.1.2.build.+")
    implementation(project(":shared"))
}

kotlin {
    jvmToolchain(25)
}