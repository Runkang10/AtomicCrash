import java.net.URI

plugins {
    alias(libs.plugins.kotlin.jvm)
    //alias(libs.plugins.paperweightUserdev)
}

abstract class DownloadFile : DefaultTask() {
    @get:Input
    abstract val sourceUrl: Property<String>

    @get:OutputFile
    abstract val destination: RegularFileProperty

    @TaskAction
    fun download() {
        val dest = destination.get().asFile
        if (dest.exists()) {
            logger.lifecycle("$dest already exists, skipping download.")
            return
        }
        URI(sourceUrl.get()).toURL().openStream().use { input ->
            dest.outputStream().use { output -> input.copyTo(output) }
        }
        logger.lifecycle("Downloaded Minecraft client jar to $dest")
    }
}

val downloadClient = tasks.register<DownloadFile>("downloadClient") {
    description = "Download Minecraft 26.3 client jar."
    sourceUrl.set("https://piston-data.mojang.com/v1/objects/a64d116707456e8c6069776a558936d11a2674e1/client.jar")
    destination.set(file("client.jar"))
}

dependencies {
    compileOnly(files(downloadClient.map { it.destination }))
    compileOnly("io.papermc.paper:paper-api:26.2.build.+")
    compileOnly(project(":shared"))
}

tasks.compileKotlin {
    dependsOn(downloadClient)
}