plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.paperweightUserdev)
}

dependencies {
    paperweight.paperDevBundle("26.2.build.+")
    compileOnly(project(":shared"))
}