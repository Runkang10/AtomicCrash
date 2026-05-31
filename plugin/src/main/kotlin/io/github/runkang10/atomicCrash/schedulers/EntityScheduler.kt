package io.github.runkang10.atomicCrash.schedulers

import io.papermc.paper.threadedregions.scheduler.ScheduledTask
import org.bukkit.entity.Entity
import org.bukkit.plugin.java.JavaPlugin

class EntityScheduler(
    private val plugin: JavaPlugin?
) {
    fun run(
        entity: Entity,
        scheduledTask: (ScheduledTask) -> Unit,
        retired: (() -> Unit)? = {}
    ): ScheduledTask? {
        val scheduler = entity.scheduler
        return plugin?.let { scheduler.run(it, scheduledTask, retired) }
    }

    fun runDelayed(
        entity: Entity,
        scheduledTask: (ScheduledTask) -> Unit,
        retired: (() -> Unit)? = {},
        delay: Long
    ): ScheduledTask? {
        val scheduler = entity.scheduler
        return plugin?.let { scheduler.runDelayed(it, scheduledTask, retired, delay) }
    }

    fun runAtFixedRate(
        entity: Entity,
        scheduledTask: (ScheduledTask) -> Unit,
        retired: (() -> Unit)? = {},
        initialDelay: Long,
        delay: Long
    ): ScheduledTask? {
        val scheduler = entity.scheduler
        return plugin?.let { scheduler.runAtFixedRate(it, scheduledTask, retired, initialDelay, delay) }
    }
}