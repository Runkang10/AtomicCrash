package io.github.atomicCrash

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class AtomicCrash : JavaPlugin(), Listener {
    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
    }
}