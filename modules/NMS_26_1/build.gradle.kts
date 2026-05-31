plugins {
    kotlin("jvm")
    id("io.papermc.paperweight.userdev")
}

kotlin {
    jvmToolchain(25)
}

dependencies {
    paperweight.paperDevBundle("26.1.2.build.+")
    implementation(project(":shared"))
}
