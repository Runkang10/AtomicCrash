rootProject.name = "AtomicCrash"

include(":shared", ":modules", ":plugin")
rootDir.resolve("modules").listFiles()
    .filter { it.isDirectory }
    .forEach { include(":modules:${it.name}") }