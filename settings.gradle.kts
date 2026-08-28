rootProject.name = "AtomicCrash"

include("plugin", "shared")
rootDir.resolve("versions").listFiles { it.isDirectory }?.forEach { include("versions:${it.name}") }