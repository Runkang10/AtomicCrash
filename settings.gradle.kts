plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "AtomicCrash"

include("plugin", "shared")
rootDir.resolve("versions").listFiles { it.isDirectory }?.forEach { include("versions:${it.name}") }