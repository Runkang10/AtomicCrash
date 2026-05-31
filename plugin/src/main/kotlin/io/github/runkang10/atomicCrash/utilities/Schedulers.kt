package io.github.runkang10.atomicCrash.utilities

import io.github.runkang10.atomicCrash.schedulers.AsyncScheduler
import io.github.runkang10.atomicCrash.schedulers.EntityScheduler
import io.github.runkang10.atomicCrash.schedulers.GlobalRegionScheduler
import org.bukkit.plugin.java.JavaPlugin

object Schedulers {
    lateinit var async: AsyncScheduler
        private set
    lateinit var entity: EntityScheduler
        private set
    lateinit var global: GlobalRegionScheduler
        private set

    fun load(plugin: JavaPlugin) {
        async = AsyncScheduler(plugin)
        entity = EntityScheduler(plugin)
        global = GlobalRegionScheduler(plugin)
    }
}